package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.LikeT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for the LikeT entity.
 */
@SuppressWarnings("unused")
public interface LikeTRepositoryCustom extends JpaRepository<LikeT,Long> {


    @Query(value = "SELECT * FROM like_t as lt\n" +
        "where lt.id_usuariol_id =:idUsuario AND lt.id_likep_id =:idPublicacion",nativeQuery = true)
    LikeT findLikeByUser(@Param("idUsuario") Long idUsuario, @Param("idPublicacion") Long idPublicacion);

    @Query(value = "SELECT * FROM like_t as lt\n" +
        "where lt.id_usuariol_id =:idUsuario",nativeQuery = true)
    List<LikeT> findLikesByUser(@Param("idUsuario") Long idUsuario);

}
