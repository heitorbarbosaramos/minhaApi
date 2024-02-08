package com.sisweb.api.security;

import com.sisweb.api.entity.Usuario;
import com.sisweb.api.entity.UsuarioPerfil;
import com.sisweb.api.entity.dto.UsuarioDTO;
import com.sisweb.api.entity.dto.UsuarioFormDTO;
import com.sisweb.api.enumeration.Perfil;
import com.sisweb.api.mapper.UsuarioMapper;
import com.sisweb.api.security.dto.*;
import com.sisweb.api.security.feign.FacebookCliente;
import com.sisweb.api.security.feign.GitHubCliente;
import com.sisweb.api.security.feign.GoogleApisCliente;
import com.sisweb.api.security.feign.GoogleTokenCliente;
import com.sisweb.api.service.UsuarioPerfilService;
import com.sisweb.api.service.UsuarioService;
import com.sisweb.api.service.util.GeradorSenha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private final FacebookCliente facebookCliente;
    private final GoogleTokenCliente googleTokenCliente;
    private final GoogleApisCliente googleApisCliente;

    @Value("${config.jwt.secret}")
    private String secreto;

    @Value("${SSO_GOOGLE_ID}")
    private String googleId;
    @Value("${SSO_GOOGLE_SECRET}")
    private String googleSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedrect;
    @Value("${SSO_GITHUB_ID}")
    private String gitHubId;
    @Value("${SSO_GITHUB_SECRET}")
    private String gitHubSecret;
    @Value("${SSO_FACEBOOK_ID}")
    private String facebookId;
    @Value("${SSO_FACEBOOK_SECRET}")
    private String facebookSecret;
    @Value("${spring.security.oauth2.client.registration.facebook.redirect-uri}")
    private String facebookRedirect;
    @Value("${app.sistema.uri}")
    private String redirect;

    @GetMapping("/oauth2/code/google")
    public String loginComGoogle(@Param("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {

        GoogleTokenDTO tokenDTO = new GoogleTokenDTO(code, googleId, googleSecret, googleRedrect,"authorization_code", "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile openid");

        GoogleAcessTokenDTO token = googleTokenCliente.getGoogleToken(tokenDTO);
        GoogleUserDetailsDTO userInfo = googleApisCliente.getUserInfo(token.getAccess_token());

        Usuario usuario = usuarioService.findByLoginGoogle(userInfo.getEmail());

        efetuarLoginSocial(usuario, Perfil.ROLE_USUARIO_GOOGLE, null, null, userInfo, request, response);
//
//        return String.format("=>PRINCIPAL: %s " +
//                "\n => Nome: %s" +
//                "\n => Email: %s ",
//                principal,
//                principal.getAttribute("name"),
//                principal.getAttribute("email")
//                );
        return "";
    }

    @GetMapping("/oauth2/code/github")
    public String loginComGitHub(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {

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

        efetuarLoginSocial(usuario, Perfil.ROLE_USUARIO_GITHUB,null,gitUserDTO, null, request, response);
        return "CODE: " + code;
    }

    @GetMapping("/oauth2/code/facebook")
    public String loginComFacebook(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("CODE FACEBOOK {}", code);

        FacebookGetTokenDTO getTokenDTO = new FacebookGetTokenDTO();
        getTokenDTO.setClient_id(facebookId);
        getTokenDTO.setClient_secret(facebookSecret);
        getTokenDTO.setRedirect_uri(facebookRedirect);
        getTokenDTO.setCode(code);

        FacebookTokenDTO facebookTokenDTO = facebookCliente.getToken(getTokenDTO);

        FacebookUserDetailsDTO facebookUserDetailsDTO = facebookCliente.getFacebookUserDetails(facebookTokenDTO.getAccess_token());

        Usuario usuario = usuarioService.findByLoginFacebook(facebookUserDetailsDTO.getEmail());

        efetuarLoginSocial(usuario, Perfil.ROLE_USUARIO_FACEBOOK, facebookUserDetailsDTO, null,null, request, response);
        return "FACEBOOK: " + code;
    }

    private RuntimeException efetuarLoginSocial(Usuario usuario, Perfil perfil, FacebookUserDetailsDTO facebookUserDetailsDTO, GitHubUserDetailsDTO gitUserDTO, GoogleUserDetailsDTO googleUser, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(!usuario.getAtivo()){
            new RuntimeException("Usuário desabilitado");
            response.setStatus(500);
            response.setHeader("message:" , "Usuário desabilitado");
            response.sendRedirect(redirect);
        }

        UsuarioFormDTO dto = new UsuarioFormDTO();
        dto.setUpdateSenha(false);

        UsuarioDTO usuarioDTO = null;

        String login = null;
        String nome = null;

        if(facebookUserDetailsDTO != null){

            login = facebookUserDetailsDTO.getEmail();
            nome = facebookUserDetailsDTO.getName();
            dto.setLoginEmailFaceBook(facebookUserDetailsDTO.getEmail());
            usuario.setLoginEmailFaceBook(login);

        } else if (gitUserDTO != null) {

            login = gitUserDTO.getEmail();
            nome = gitUserDTO.getName();
            dto.setLoginGitHub(gitUserDTO.getLogin());
            dto.setLoginEmailGitHub(gitUserDTO.getEmail());
            usuario.setLoginGitHub(gitUserDTO.getLogin());

        } else if (googleUser != null) {

            login = googleUser.getEmail();
            nome = googleUser.getName();
            dto.setLoginEmailGoogle(googleUser.getEmail());
            usuario.setLoginEmailGoogle(login);
        }

        if(usuario == null){

            Set<Long> idsPerfir = new HashSet<>();
            idsPerfir.add( Long.parseLong(String.valueOf(Perfil.ROLE_USUARIO.getCod())));
            idsPerfir.add( Long.parseLong(String.valueOf(perfil.getCod())));

            dto.setLogin(login);
            dto.setNome(nome);
            dto.setAtivo(true);
            dto.setIdsPerfis(idsPerfir);
            dto.setSenha(GeradorSenha.criar());
            dto.setUpdateSenha(true);

            usuarioDTO = usuarioService.createUpdate(dto);

            usuario = usuarioMapper.toEntity(usuarioDTO);
        }else {
            UsuarioPerfil perfilUsuario = perfilService.findByPerfil(perfil);

            dto.getIdsPerfis().add(perfilUsuario.getId());
            usuario.getPerfis().add(perfilUsuario);

            dto = usuarioMapper.fromUsuario(usuario);
            for(UsuarioPerfil x : usuario.getPerfis()){
                dto.getIdsPerfis().add(x.getId());
            }

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
                return false;
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

        log.info("AUTENTICACAO COM {}", perfil.getDescricao());

        autenticacaoController.efetuarLogin(request, response, loginDTO);
        return null;
    }
}
