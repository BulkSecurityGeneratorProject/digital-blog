package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Nota;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Nota entity.
 */
@SuppressWarnings("unused")
public interface NotaRepository extends JpaRepository<Nota,Long> {

}
