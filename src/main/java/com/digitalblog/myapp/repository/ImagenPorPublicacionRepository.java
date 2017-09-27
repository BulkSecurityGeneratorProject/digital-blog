package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.ImagenPorPublicacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ImagenPorPublicacion entity.
 */
@SuppressWarnings("unused")
public interface ImagenPorPublicacionRepository extends JpaRepository<ImagenPorPublicacion,Long> {

}
