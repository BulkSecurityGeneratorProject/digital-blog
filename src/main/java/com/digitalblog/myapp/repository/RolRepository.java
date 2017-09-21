package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Rol;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Rol entity.
 */
@SuppressWarnings("unused")
public interface RolRepository extends JpaRepository<Rol,Long> {

}
