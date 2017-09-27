package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.ImagenPorPublicacion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ImagenPorPublicacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagenPorPublicacionRepository extends JpaRepository<ImagenPorPublicacion, Long> {

}
