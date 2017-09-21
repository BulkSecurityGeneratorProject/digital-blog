package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.RolePermiso;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RolePermiso entity.
 */
@SuppressWarnings("unused")
public interface RolePermisoRepository extends JpaRepository<RolePermiso,Long> {

}
