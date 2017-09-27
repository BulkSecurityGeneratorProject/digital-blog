package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.PublicacionReportadaDTO;
import java.util.List;

/**
 * Service Interface for managing PublicacionReportada.
 */
public interface PublicacionReportadaService {

    /**
     * Save a publicacionReportada.
     *
     * @param publicacionReportadaDTO the entity to save
     * @return the persisted entity
     */
    PublicacionReportadaDTO save(PublicacionReportadaDTO publicacionReportadaDTO);

    /**
     *  Get all the publicacionReportadas.
     *
     *  @return the list of entities
     */
    List<PublicacionReportadaDTO> findAll();

    /**
     *  Get the "id" publicacionReportada.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PublicacionReportadaDTO findOne(Long id);

    /**
     *  Delete the "id" publicacionReportada.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
