package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.CapituloDTO;
import java.util.List;

/**
 * Service Interface for managing Capitulo.
 */
public interface CapituloService {

    /**
     * Save a capitulo.
     *
     * @param capituloDTO the entity to save
     * @return the persisted entity
     */
    CapituloDTO save(CapituloDTO capituloDTO);

    /**
     *  Get all the capitulos.
     *  
     *  @return the list of entities
     */
    List<CapituloDTO> findAll();

    /**
     *  Get the "id" capitulo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CapituloDTO findOne(Long id);

    /**
     *  Delete the "id" capitulo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
