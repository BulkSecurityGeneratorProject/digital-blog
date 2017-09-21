package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Seguidor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Seguidor entity.
 */
@SuppressWarnings("unused")
public interface SeguidorRepository extends JpaRepository<Seguidor,Long> {

}
