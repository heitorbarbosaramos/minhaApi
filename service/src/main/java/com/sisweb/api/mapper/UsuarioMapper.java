package com.sisweb.api.mapper;

import com.sisweb.api.entity.Endereco;
import com.sisweb.api.entity.Usuario;
import com.sisweb.api.entity.dto.UsuarioDTO;
import com.sisweb.api.entity.dto.UsuarioFormDTO;
import com.sisweb.api.entity.dto.UsuarioResetaSenhaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {Usuario.class, UsuarioFormDTO.class, Endereco.class, UsuarioResetaSenhaDTO.class})
public interface UsuarioMapper {

    @Mapping(target = "dto.updateSenha", ignore = true)
    Usuario fromFormDTO(UsuarioFormDTO dto);

    UsuarioDTO toDTO(Usuario entity);

    Usuario toEntity(UsuarioDTO dto);

    UsuarioResetaSenhaDTO toDTOResetaSenha(Usuario entity);

    UsuarioFormDTO fromUsuario(Usuario usuario);
}
