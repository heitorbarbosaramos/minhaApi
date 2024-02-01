package com.sisweb.api.security;

import com.sisweb.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Value("${app.sistema.uri}")
    private String redirect;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CookieService cookieService;

    @PostMapping
    @Operation(tags = {"Autenticacao"}, summary = "Realizar login através de um usuário e senha",
            description = "Requisição POST para Realizar login através de um usuário e senha", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity efetuarLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid LoginDTO dto){
        log.info("REQUISICAO POST PARA REALIZAR LOGIN {} {}", dto.getLogin(), dto.getSenha());


        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());
        var authentication = dto.getSocial() == null ? manager.authenticate(authenticationToken) : dto.getSocial();

        String tokenJWT = tokenService.gerarToken(dto.getSocial() == null ? (UsuarioSpringSecurity) authentication.getPrincipal() : dto.getSocialUss());

        Set<String> perfis = new HashSet<>();

        for(GrantedAuthority x : dto.getSocial() == null ? ((UsuarioSpringSecurity) authentication.getPrincipal()).getAuthorities() : dto.getSocial().getAuthorities()){
            perfis.add(x.getAuthority());
        }

        cookieService.createCookiePerfil(request, response, perfis);
        cookieService.createCookieToken(request, response, tokenJWT);
        cookieService.createCookieLogin(request, response, dto.getSocial() == null ? ((UsuarioSpringSecurity) authentication.getPrincipal()).getLogin() : dto.getLogin() );
        cookieService.createCookieNome(request, response, dto.getSocial() == null ? ((UsuarioSpringSecurity) authentication.getPrincipal()).getNome() : dto.getSocial().getName());

        DadosTokenJWT dadosTokenJWT = new DadosTokenJWT(
                tokenJWT,
                dto.getSocial() == null ? ((UsuarioSpringSecurity) authentication.getPrincipal()).getLogin() : dto.getLogin(),
                dto.getSocial() == null ? ((UsuarioSpringSecurity) authentication.getPrincipal()).getNome() : dto.getSocial().getName(),
                perfis);

        if(dto.getSocial() != null){
            try {
                response.sendRedirect(redirect);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return ResponseEntity.ok(dadosTokenJWT);
    }

    @GetMapping("/logout")
    public void logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        cookieService.apagaCookies(request, response);
        response.sendRedirect(redirect);
    }
}
