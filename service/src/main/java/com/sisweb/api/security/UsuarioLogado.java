package com.sisweb.api.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioLogado {

    public static UsuarioSpringSecurity usuarioLogado(){

        UsuarioSpringSecurity uss = (UsuarioSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return uss;
    }
}
