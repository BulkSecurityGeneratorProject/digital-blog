package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Respuesta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Respuesta entity.
 */
@SuppressWarnings("unused")
public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {

}
