package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Pagina;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pagina entity.
 */
@SuppressWarnings("unused")
public interface PaginaRepository extends JpaRepository<Pagina,Long> {

}
