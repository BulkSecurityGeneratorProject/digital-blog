package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.PublicacionReportada;
import com.digitalblog.myapp.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the PublicacionReportada entity.
 */
@SuppressWarnings("unused")
public interface PublicacionReportadaRepositoryCustom extends JpaRepository<PublicacionReportada, Long> {

    /**
     * Trae todas las publicaciones reportes donde el id_publicacion se igual al idPublicacion
     *
     * @param idPublicacion id de la publicacion
     * @return entity PublicacionReportada
     */
    @Query(value = "Select * from publicacion_reportada as pr\n" +
        "Where pr.id_publicacion =:idPublicacion", nativeQuery = true)
    PublicacionReportada findPublicacionPorIdPublicacion(@Param("idPublicacion") Integer idPublicacion);

}
