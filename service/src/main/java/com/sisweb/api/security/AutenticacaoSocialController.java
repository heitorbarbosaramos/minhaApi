package com.sisweb.api.security;

import com.sisweb.api.entity.Usuario;
import com.sisweb.api.entity.UsuarioPerfil;
import com.sisweb.api.entity.dto.UsuarioDTO;
import com.sisweb.api.entity.dto.UsuarioFormDTO;
import com.sisweb.api.enumeration.Perfil;
import com.sisweb.api.mapper.UsuarioMapper;
import com.sisweb.api.security.dto.GitHubUserDetailsDTO;
import com.sisweb.api.security.feign.GitHubCliente;
import com.sisweb.api.service.UsuarioPerfilService;
import com.sisweb.api.service.UsuarioService;
import com.sisweb.api.service.util.GeradorSenha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/loginSocial")
@RequiredArgsConstructor
public class AutenticacaoSocialController {

    private final UsuarioService usuarioService;
    private final AutenticacaoController autenticacaoController;
    private final UsuarioMapper usuarioMapper;
    private final GitHubCliente gitHubCliente;
    private final UsuarioPerfilService perfilService;

    @Value("${config.jwt.secret}")
    private String secreto;

    @Value("${SSO_GITHUB_ID}")
    private String gitHubId;
    @Value("${SSO_GITHUB_SECRET}")
    private String gitHubSecret;

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

    @GetMapping("/oauth2/code/github")
    public String loginComGitHub(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construir o corpo da solicitação para trocar o código pelo token de acesso
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("client_id", gitHubId);
        requestBody.put("client_secret", gitHubSecret);
        requestBody.put("code", code);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Enviar a solicitação para obter o token de acesso
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(
                "https://github.com/login/oauth/access_token",
                requestEntity,
                Map.class
        );

        // Extrair o token de acesso do corpo da resposta
        String accessToken = "Bearer " + (String) responseEntity.getBody().get("access_token");

        String apiUrl = "https://api.github.com/user";

        headers.setBearerAuth(accessToken);

        GitHubUserDetailsDTO gitUserDTO = gitHubCliente.getUserDetails(accessToken);

        Usuario usuario = usuarioService.findByLoginGitHub(gitUserDTO.getEmail(), gitUserDTO.getLogin(), gitUserDTO.getName());

        UsuarioFormDTO dto = new UsuarioFormDTO();
        UsuarioDTO usuarioDTO = null;

        if(usuario == null){

            Set<Long> idsPerfir = new HashSet<>();
            idsPerfir.add( Long.parseLong(String.valueOf(Perfil.ROLE_USUARIO.getCod())));
            idsPerfir.add( Long.parseLong(String.valueOf(Perfil.ROLE_USUARIO_GOOGLE.getCod())));

            dto.setLogin(gitUserDTO.getEmail());
            dto.setLoginGitHub(gitUserDTO.getLogin());
            dto.setNome(gitUserDTO.getName());
            dto.setAtivo(true);
            dto.setIdsPerfis(idsPerfir);
            dto.setSenha(GeradorSenha.criar());

            usuarioDTO = usuarioService.createUpdate(dto);

            usuario = usuarioMapper.toEntity(usuarioDTO);
        }else {
            UsuarioPerfil perfil = perfilService.findByPerfil(Perfil.ROLE_USUARIO_GITHUB);
            usuario.getPerfis().add(perfil);

            dto = usuarioMapper.fromUsuario(usuario);
            for(UsuarioPerfil x : usuario.getPerfis()){
                dto.getIdsPerfis().add(x.getId());
            }

            dto.setLoginGitHub(gitUserDTO.getLogin());
            dto.setLoginEmailGitHub(gitUserDTO.getEmail());
            dto.setUpdateSenha(false);
            usuarioDTO = usuarioService.createUpdate(dto);
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

        log.info("AUTENTICACAO COM O GITHUB {} -  {} - {}", usuarioDTO.getLogin(), usuarioDTO.getLoginEmailGitHub(), usuarioDTO.getLoginGitHub());

        autenticacaoController.efetuarLogin(request, response, loginDTO);

        return "Hello, " + code;
    }
}
