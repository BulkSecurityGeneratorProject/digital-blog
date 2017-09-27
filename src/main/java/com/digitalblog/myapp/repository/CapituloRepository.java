package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Capitulo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Capitulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapituloRepository extends JpaRepository<Capitulo, Long> {

}
