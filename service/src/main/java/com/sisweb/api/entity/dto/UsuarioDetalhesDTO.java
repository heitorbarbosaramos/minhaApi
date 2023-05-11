package com.sisweb.api.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UsuarioDetalhesDTO {

    private Integer quantUsuario;
    private Integer quantUsuarioAtivo;
    private Integer quantUsuarioInativo;
    private Integer quantUsuarioSenhaResta;
    private List<String> quantPerfilGroup = new ArrayList<>();

    public void addQuantPerfilGroup(String nomePerfil, Integer quantPerfil){
        this.quantPerfilGroup.add(nomePerfil + ": " +quantPerfil);
    }


}
