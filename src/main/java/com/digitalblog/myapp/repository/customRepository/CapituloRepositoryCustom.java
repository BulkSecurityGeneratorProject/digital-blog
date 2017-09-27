package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Capitulo;
import com.digitalblog.myapp.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;


public interface CapituloRepositoryCustom extends JpaRepository<Usuario,Long> {

    @Query("Select c from Capitulo c where c.idPublicacionC.id = :id")
    ArrayList<Capitulo> findByIdPublicacion(@Param("id") Long id);

}
