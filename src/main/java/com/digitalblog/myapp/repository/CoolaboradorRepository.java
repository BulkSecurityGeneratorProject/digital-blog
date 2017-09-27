package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Coolaborador;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Coolaborador entity.
 */
@SuppressWarnings("unused")
public interface CoolaboradorRepository extends JpaRepository<Coolaborador,Long> {

}
