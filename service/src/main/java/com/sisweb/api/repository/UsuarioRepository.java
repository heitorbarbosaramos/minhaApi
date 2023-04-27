package com.sisweb.api.repository;

import com.sisweb.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("Select u from Usuario u where u.login = :login")
    Usuario findByLogin(@Param("login") String login);
}
