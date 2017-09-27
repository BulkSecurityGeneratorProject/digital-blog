package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Coolaborador;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Coolaborador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoolaboradorRepository extends JpaRepository<Coolaborador, Long> {

}
