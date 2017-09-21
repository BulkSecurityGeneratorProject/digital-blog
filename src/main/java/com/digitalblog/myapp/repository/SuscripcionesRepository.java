package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Suscripciones;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Suscripciones entity.
 */
@SuppressWarnings("unused")
public interface SuscripcionesRepository extends JpaRepository<Suscripciones,Long> {

}
