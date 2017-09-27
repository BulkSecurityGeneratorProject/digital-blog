package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.SeccionPublicacionDTO;
import java.util.List;

/**
 * Service Interface for managing SeccionPublicacion.
 */
public interface SeccionPublicacionService {

    /**
     * Save a seccionPublicacion.
     *
     * @param seccionPublicacionDTO the entity to save
     * @return the persisted entity
     */
    SeccionPublicacionDTO save(SeccionPublicacionDTO seccionPublicacionDTO);

    /**
     *  Get all the seccionPublicacions.
     *
     *  @return the list of entities
     */
    List<SeccionPublicacionDTO> findAll();

    /**
     *  Get the "id" seccionPublicacion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SeccionPublicacionDTO findOne(Long id);

    /**
     *  Delete the "id" seccionPublicacion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
