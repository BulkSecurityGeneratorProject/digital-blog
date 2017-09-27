package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Permiso;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Permiso entity.
 */
@SuppressWarnings("unused")
public interface PermisoRepository extends JpaRepository<Permiso,Long> {

}
