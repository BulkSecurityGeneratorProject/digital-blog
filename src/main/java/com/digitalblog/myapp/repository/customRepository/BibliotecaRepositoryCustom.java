package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Biblioteca entity.
 */
@SuppressWarnings("unused")
public interface BibliotecaRepositoryCustom extends JpaRepository<Biblioteca,Long> {

     @Query("Select b from Biblioteca b where b.idJhiUser = :id")
     Biblioteca findBibliotecaByIdJhiUser(@Param("id") Long id);
}
