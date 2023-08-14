package com.sisweb.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisweb.api.entity.Endereco;
import com.sisweb.api.entity.UsuarioPerfil;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;
    private String login;
    @JsonIgnore
    private String senha;
    private String nome;
    private Boolean ativo;
    private String loginEmailGoogle;
    private String loginEmailGitHub;
    private String loginGitHub;

    private Set<UsuarioPerfil> perfis = new HashSet<>();
    private Endereco endereco;
    Set<String> fone = new HashSet<>();
}
