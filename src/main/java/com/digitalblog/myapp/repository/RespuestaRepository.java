package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Respuesta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Respuesta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

}
