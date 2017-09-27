package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Seccion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Seccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Long> {

}
