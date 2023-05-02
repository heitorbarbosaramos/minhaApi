package com.sisweb.api.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class DadosTokenJWT {

    private String tokenJWT;
    private String login;
    private String nome;
    private Set<String> perfis = new HashSet<>();
}