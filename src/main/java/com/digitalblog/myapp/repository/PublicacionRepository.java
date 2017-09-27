package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Publicacion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Publicacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

}
