package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.TemaDTO;
import java.util.List;

/**
 * Service Interface for managing Tema.
 */
public interface TemaService {

    /**
     * Save a tema.
     *
     * @param temaDTO the entity to save
     * @return the persisted entity
     */
    TemaDTO save(TemaDTO temaDTO);

    /**
     *  Get all the temas.
     *  
     *  @return the list of entities
     */
    List<TemaDTO> findAll();

    /**
     *  Get the "id" tema.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TemaDTO findOne(Long id);

    /**
     *  Delete the "id" tema.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
