package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by suhu2 on 4/3/2017.
 */
public interface ComentarioSobrePublicacionRepositoryCustom extends JpaRepository<Comentario,Long> {
    @Query(value = "select * from comentario where id_comentariop_id = :idPublicacion",nativeQuery = true)
    List<Comentario> findByIdPublicacion(@Param("idPublicacion")Long idPublicacion);
}
