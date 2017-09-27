package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.SeccionPublicacion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SeccionPublicacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeccionPublicacionRepository extends JpaRepository<SeccionPublicacion, Long> {

}
