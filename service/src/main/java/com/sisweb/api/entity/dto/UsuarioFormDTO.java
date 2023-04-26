package com.sisweb.api.entity.dto;

import com.sisweb.api.entity.Endereco;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UsuarioFormDTO {

    @NotEmpty(message = "Campo obrigatório")
    private String login;
    @NotEmpty(message = "Campo obrigatório")
    private String senha;
    @NotNull(message = "Campo obrigatório")
    private Set<Long> idsPerfis = new HashSet<>();
    private Endereco endereco;
}
