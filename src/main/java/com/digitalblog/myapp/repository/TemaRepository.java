package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Tema;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tema entity.
 */
@SuppressWarnings("unused")
public interface TemaRepository extends JpaRepository<Tema,Long> {

}
