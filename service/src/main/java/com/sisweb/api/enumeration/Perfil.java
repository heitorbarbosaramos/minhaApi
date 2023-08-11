package com.sisweb.api.enumeration;

public enum Perfil {
    ROLE_USUARIO (1, "Usuário"),
    ROLE_ADMIN (2, "Administrador"),
    ROLE_USUARIO_GOOGLE (3, "Usuário Google"),
    ROLE_USUARIO_FACEBOOK (4, "Usuário FaceBook"),
    ROLE_USUARIO_GITHUB(5, "Usuário GitHub");

    int cod;
    String descricao;

    Perfil(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }
}
