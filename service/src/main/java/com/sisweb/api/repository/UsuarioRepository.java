package com.sisweb.api.repository;

import com.sisweb.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("Select u from Usuario u where u.login = :login")
    Usuario findByLogin(@Param("login") String login);

    @Query(value = "select " +
            "   per.nome, count(rol.id_perfil)" +
            " from " +
            "   tb_usuario_perfil per " +
            "   inner join tb_usuario_roles rol on per.id = rol.id_perfil " +
            " group by " +
            "   rol.id_perfil, per.nome", nativeQuery = true)
    List<String> perfilGroup();

    Usuario findByLoginEmailGoogle(String loginGoogle);
}
