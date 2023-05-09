package com.sisweb.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UsuarioResetaSenhaDTO {

    @NotNull(message = "Campo obrigatório")
    private Long id;
    @NotNull(message = "Campo obrigatório")
    private Long timestampRecuperaSenha;
    private String nome;
    private String login;
    @NotNull(message = "Campo obrigatório")
    private String senha;
}
