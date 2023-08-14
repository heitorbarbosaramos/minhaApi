package com.sisweb.api.repository;

import com.sisweb.api.entity.UsuarioPerfil;
import com.sisweb.api.enumeration.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, Long> {
    UsuarioPerfil findByPerfil(Perfil perfil);
}
