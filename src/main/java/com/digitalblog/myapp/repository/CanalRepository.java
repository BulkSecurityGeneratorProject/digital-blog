package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Canal;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Canal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CanalRepository extends JpaRepository<Canal, Long> {

}
