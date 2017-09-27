package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Biblioteca;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Biblioteca entity.
 */
@SuppressWarnings("unused")
public interface BibliotecaRepository extends JpaRepository<Biblioteca,Long> {

}
