package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Capitulo;
import com.digitalblog.myapp.domain.Pagina;
import com.digitalblog.myapp.domain.Publicacion;
import com.digitalblog.myapp.domain.SeccionPublicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the SeccionPublicacion entity.
 */
@SuppressWarnings("unused")
public interface SeccionPublicacionRepositoryCustom extends JpaRepository<SeccionPublicacion,Long> {

        @Query(value = "SELECT * FROM seccion_publicacion as sp\n" +
           " WHERE sp.id_seccionsp_id =:idSeccion AND sp.id_publicacionsp_id =:idPublicacion",nativeQuery = true)
    SeccionPublicacion findPublicacionBySeccion(@Param("idSeccion")Long idSeccion,@Param("idPublicacion")Long idPublicacion);
}
