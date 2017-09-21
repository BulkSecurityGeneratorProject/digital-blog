package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Capitulo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Capitulo entity.
 */
@SuppressWarnings("unused")
public interface CapituloRepository extends JpaRepository<Capitulo,Long> {

}
