package com.sisweb.api.entity.dto;

import com.sisweb.api.enumeration.Perfil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UsuarioPerfilDTO {

    private Long id;
    @NotEmpty(message = "Campo obrigatório")
    private String nome;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Campo obrigatório")
    private Perfil perfil;
}
