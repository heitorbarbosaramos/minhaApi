package com.sisweb.api.security;

import com.sisweb.api.entity.UsuarioPerfil;
import com.sisweb.api.enumeration.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioSpringSecurity implements UserDetails {

    private Long id;
    private String login;
    private String senha;
    private String nome;
    private Boolean ativo;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioSpringSecurity(){
    }

    public UsuarioSpringSecurity(Long id, String login, String senha, String nome, Boolean ativo , Set<UsuarioPerfil> perfis){
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.ativo = ativo;
        this.authorities = perfis.stream().map(item -> new SimpleGrantedAuthority(item.getPerfil().name())).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getNome() {
        return nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }

    public boolean temPerfil(Perfil perfil) {

        return getAuthorities().contains(new SimpleGrantedAuthority(perfil.name())) ? true : false;

    }

    public String findPerfisString(){
        String perfis = "";
        for(GrantedAuthority x : this.authorities){
            perfis += x.getAuthority() + " - ";
        }
        perfis = perfis.substring(0, perfis.length()-3);
        return perfis;
    }
}
