package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.Notificacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Notificacion entity.
 */
@SuppressWarnings("unused")
public interface NotificacionRepository extends JpaRepository<Notificacion,Long> {

}
