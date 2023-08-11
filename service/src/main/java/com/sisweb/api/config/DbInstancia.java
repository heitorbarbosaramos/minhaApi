package com.sisweb.api.config;

import com.sisweb.api.entity.dto.UsuarioPerfilDTO;
import com.sisweb.api.enumeration.Perfil;
import com.sisweb.api.service.UsuarioPerfilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DbInstancia {

    private final UsuarioPerfilService perfilService;

    public void instanciandoPerfis(){

        if(!perfilService.findAll(0, 24, "id", "ASC").isEmpty()){
            return;
        }

        log.info("INSTANCIANDO PERFIS");

        UsuarioPerfilDTO dto = new UsuarioPerfilDTO();
        dto.setNome(Perfil.ROLE_USUARIO.getDescricao());
        dto.setPerfil(Perfil.ROLE_USUARIO);
        perfilService.save(dto);

        dto = new UsuarioPerfilDTO();
        dto.setNome(Perfil.ROLE_ADMIN.getDescricao());
        dto.setPerfil(Perfil.ROLE_ADMIN);
        perfilService.save(dto);

        dto = new UsuarioPerfilDTO();
        dto.setNome(Perfil.ROLE_USUARIO_GOOGLE.getDescricao());
        dto.setPerfil(Perfil.ROLE_USUARIO_GOOGLE);
        perfilService.save(dto);

        dto = new UsuarioPerfilDTO();
        dto.setNome(Perfil.ROLE_USUARIO_FACEBOOK.getDescricao());
        dto.setPerfil(Perfil.ROLE_USUARIO_FACEBOOK);
        perfilService.save(dto);

        dto = new UsuarioPerfilDTO();
        dto.setNome(Perfil.ROLE_USUARIO_GITHUB.getDescricao());
        dto.setPerfil(Perfil.ROLE_USUARIO_GITHUB);
        perfilService.save(dto);

        log.info("FIM INSTANCIANDO PERFIS");
    }
}
