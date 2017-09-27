package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Publicacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Publicacion entity.
 */
@SuppressWarnings("unused")
public interface PublicacionRepository extends JpaRepository<Publicacion,Long> {

}
