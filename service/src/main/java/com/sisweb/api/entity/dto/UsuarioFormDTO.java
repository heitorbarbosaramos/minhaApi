package com.sisweb.api.entity.dto;

import com.sisweb.api.entity.Endereco;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UsuarioFormDTO {

    private Long id;
    @NotEmpty(message = "Campo obrigatório")
    @Email(message = "Não é um email valido")
    private String login;
    @NotEmpty(message = "Campo obrigatório")
    private String senha;
    @NotEmpty(message = "Campo obrigatório")
    private String nome;
    @NotNull(message = "Campo obrigatório")
    private Boolean ativo;
    @NotNull(message = "Campo obrigatório")
    private Set<Long> idsPerfis = new HashSet<>();
    private Endereco endereco;
    Set<String> fone = new HashSet<>();
}
