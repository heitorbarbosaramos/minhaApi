package com.sisweb.api.mapper;

import com.sisweb.api.entity.UsuarioPerfil;
import com.sisweb.api.entity.dto.UsuarioPerfilDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UsuarioPerfil.class, UsuarioPerfilDTO.class})
public interface UsuarioMapper {

    UsuarioPerfil toEntity(UsuarioPerfilDTO dto);

    UsuarioPerfilDTO toDto(UsuarioPerfil entity);
}
