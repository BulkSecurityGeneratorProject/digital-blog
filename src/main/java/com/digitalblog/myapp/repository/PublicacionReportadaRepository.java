package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.PublicacionReportada;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PublicacionReportada entity.
 */
@SuppressWarnings("unused")
public interface PublicacionReportadaRepository extends JpaRepository<PublicacionReportada,Long> {

}
