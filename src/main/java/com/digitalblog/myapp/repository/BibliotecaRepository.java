package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Biblioteca;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Biblioteca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {

}
