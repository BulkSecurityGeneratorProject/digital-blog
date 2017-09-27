package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Suscripciones;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Suscripciones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuscripcionesRepository extends JpaRepository<Suscripciones, Long> {

}
