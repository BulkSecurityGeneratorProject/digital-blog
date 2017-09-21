package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.SeccionPublicacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SeccionPublicacion entity.
 */
@SuppressWarnings("unused")
public interface SeccionPublicacionRepository extends JpaRepository<SeccionPublicacion,Long> {

}
