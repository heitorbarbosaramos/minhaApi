package com.sisweb.api.security;

import com.sisweb.api.entity.Usuario;
import com.sisweb.api.entity.dto.UsuarioDTO;
import com.sisweb.api.entity.dto.UsuarioFormDTO;
import com.sisweb.api.enumeration.Perfil;
import com.sisweb.api.mapper.UsuarioMapper;
import com.sisweb.api.service.UsuarioService;
import com.sisweb.api.service.util.GeradorSenha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/loginSocial")
@RequiredArgsConstructor
public class AutenticacaoSocialController {

    private final UsuarioService usuarioService;
    private final AutenticacaoController autenticacaoController;
    private final UsuarioMapper usuarioMapper;

    @Value("${config.jwt.secret}")
    private String secreto;

    @GetMapping("/oauth2/code/google")
    public String loginComGoogle(@AuthenticationPrincipal OidcUser principal, HttpServletRequest request, HttpServletResponse response){

        Usuario usuario = usuarioService.findByLoginGoogle(principal.getAttribute("email"));

        if(usuario == null){

            Set<Long> idsPerfir = new HashSet<>();
            idsPerfir.add( Long.parseLong(String.valueOf(Perfil.ROLE_USUARIO.getCod())));
            idsPerfir.add( Long.parseLong(String.valueOf(Perfil.ROLE_USUARIO_FACEBOOK.getCod())));

            UsuarioFormDTO dto = new UsuarioFormDTO();
            dto.setLogin(principal.getAttribute("email"));
            dto.setNome(principal.getAttribute("name"));
            dto.setAtivo(true);
            dto.setLoginEmailGoogle(principal.getAttribute("email"));
            dto.setIdsPerfis(idsPerfir);
            dto.setSenha(GeradorSenha.criar());

            UsuarioDTO usuarioDTO = usuarioService.createUpdate(dto);

            usuario = usuarioMapper.toEntity(usuarioDTO);
        }

        UsuarioSpringSecurity usuarioSpringSecurity = new UsuarioSpringSecurity(usuario.getId(), usuario.getLogin(), usuario.getSenha(), usuario.getNome(), usuario.getAtivo(), usuario.getPerfis() );

        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return usuarioSpringSecurity.getAuthorities();
            }

            @Override
            public Object getCredentials() {
                return usuarioSpringSecurity.getLogin();
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return usuarioSpringSecurity.getAuthorities();
            }

            @Override
            public boolean isAuthenticated() {
                return usuarioSpringSecurity.getAtivo();
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return usuarioSpringSecurity.getNome();
            }
        };

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(usuario.getLogin());
        loginDTO.setSocial(authentication);
        loginDTO.setSocialUss(usuarioSpringSecurity);

        log.info("AUTENTICACAO COM O GOOLE {} - {} -  {} - {}", principal, principal.getAttribute("email"), principal.getAuthorities(), principal.getIdToken().getTokenValue());

        autenticacaoController.efetuarLogin(request, response, loginDTO);

        return String.format("=>PRINCIPAL: %s " +
                "\n => Nome: %s" +
                "\n => Email: %s ",
                principal,
                principal.getAttribute("name"),
                principal.getAttribute("email")
                );
    }
}
