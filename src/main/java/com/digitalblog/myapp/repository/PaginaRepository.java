package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Pagina;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pagina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaginaRepository extends JpaRepository<Pagina, Long> {

}
