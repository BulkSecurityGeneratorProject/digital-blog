package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Canal;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Canal entity.
 */
@SuppressWarnings("unused")
public interface CanalRepository extends JpaRepository<Canal,Long> {

}
