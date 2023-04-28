package com.sisweb.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${config.jwt.secret}")
    private String secreto;
    @Value("${config.jwt.expiration}")
    private Long expiracao;

    public String gerarToken(UsuarioSpringSecurity usuario){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secreto);
            return JWT.create()
                    .withIssuer("Minha Api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(LocalDateTime.now().plusHours(expiracao).toInstant(ZoneOffset.of("-03:00")))
                    .withClaim("id", usuario.getId().toString())
                    .sign(algorithm);
        } catch (JWTCreationException e){
            throw  new RuntimeException("Erro ao criar o token ", e);
        }
    }
}
