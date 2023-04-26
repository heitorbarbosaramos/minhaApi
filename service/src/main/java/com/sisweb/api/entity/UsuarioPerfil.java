package com.sisweb.api.entity;

import com.sisweb.api.enumeration.Perfil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "TB_USUARIO_PERFIL")
@EqualsAndHashCode(of = "id")
public class UsuarioPerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Campo obrigatório")
    private String nome;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Campo obrigatório")
    private Perfil perfil;
}
