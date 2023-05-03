package com.sisweb.api.security;

import com.sisweb.api.entity.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CookieService cookieService;

    @PostMapping
    public ResponseEntity efetuarLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid LoginDTO dto){
        log.info("REQUISICAO POST PARA REALIZAR LOGIN {} {}", dto.getLogin(), dto.getSenha());

        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());
        var authentication = manager.authenticate(authenticationToken);

        String tokenJWT = tokenService.gerarToken((UsuarioSpringSecurity) authentication.getPrincipal());

        ((UsuarioSpringSecurity) authentication.getPrincipal()).getAuthorities();

        Set<String> perfis = new HashSet<>();

        for(GrantedAuthority x : ((UsuarioSpringSecurity) authentication.getPrincipal()).getAuthorities()){
            perfis.add(x.getAuthority());
        }

        cookieService.createCookiePerfil(request, response, perfis);

        DadosTokenJWT dadosTokenJWT = new DadosTokenJWT(
                tokenJWT,
                ((UsuarioSpringSecurity) authentication.getPrincipal()).getLogin(),
                ((UsuarioSpringSecurity) authentication.getPrincipal()).getNome(),
                perfis);

        return ResponseEntity.ok(dadosTokenJWT);
    }


}
