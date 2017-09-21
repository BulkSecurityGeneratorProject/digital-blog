package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


public interface UsuarioRepositoryCustom extends JpaRepository<Usuario, Long> {

    @Query("Select u from Usuario u where u.idJHIUser = :id")
    Usuario findByJhiUserId(@Param("id") Long id);

    @Query("Select u from Usuario u where u.estado = :estado")
    Collection<Usuario> getAllUserWithValidState(@Param("estado") boolean estado);

    @Query(value = " Select * from usuario\n" +
        "    where id =:id", nativeQuery = true)
    Usuario findJhiUserPorIdUsuario(@Param("id") Integer id);
}
