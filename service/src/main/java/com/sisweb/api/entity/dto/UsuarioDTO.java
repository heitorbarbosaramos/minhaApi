package com.sisweb.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisweb.api.entity.Endereco;
import com.sisweb.api.entity.UsuarioPerfil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
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

    private Set<UsuarioPerfil> perfis = new HashSet<>();
    private Endereco endereco;
}
