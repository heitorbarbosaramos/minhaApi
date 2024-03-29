package com.sisweb.api.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "TB_USUARIO")
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Campo obrigatório")
    private String login;
    @NotEmpty(message = "Campo obrigatório")
    private String senha;
    @NotEmpty(message = "Campo obrigatório")
    private String nome;
    @NotNull(message = "Campo obrigatório")
    private Boolean ativo;
    @Column(name = "RECUPERA_SENHA")
    private Long timestampRecuperaSenha;
    @Column(name = "LOGIN_GOOGLE")
    private String loginEmailGoogle;
    @Column(name = "LOGIN_EMAIL_GIT_HUB")
    private String loginEmailGitHub;
    @Column(name = "LOGIN_GIT_HUB")
    private String loginGitHub;
    @Column(name = "LOGIN_EMAIL_FACEBOOK")
    private String loginEmailFaceBook;

    @NotNull(message = "Campo obrigatório")
    @NotEmpty(message = "Campo obrigatório")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TB_USUARIO_ROLES", joinColumns = @JoinColumn(name = "ID_USUARIO"), inverseJoinColumns = @JoinColumn(name = "ID_PERFIL"))
    private Set<UsuarioPerfil> perfis = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "USUARIO_TELEFONE")
    Set<String> fone = new HashSet<>();
}
