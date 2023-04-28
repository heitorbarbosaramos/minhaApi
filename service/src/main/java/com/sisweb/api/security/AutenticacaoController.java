package com.sisweb.api.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid LoginDTO dto){
        log.info("REQUISICAO POST PARA REALIZAR LOGIN {} {}", dto.getLogin(), dto.getSenha());

        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());
        var authentication = manager.authenticate(authenticationToken);

        return ResponseEntity.ok("OK-LOGOU");
    }


}
