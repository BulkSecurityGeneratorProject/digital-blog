package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.RolePermiso;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RolePermiso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RolePermisoRepository extends JpaRepository<RolePermiso, Long> {

}
