package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.SeguidorDTO;
import java.util.List;

/**
 * Service Interface for managing Seguidor.
 */
public interface SeguidorService {

    /**
     * Save a seguidor.
     *
     * @param seguidorDTO the entity to save
     * @return the persisted entity
     */
    SeguidorDTO save(SeguidorDTO seguidorDTO);

    /**
     *  Get all the seguidors.
     *  
     *  @return the list of entities
     */
    List<SeguidorDTO> findAll();

    /**
     *  Get the "id" seguidor.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SeguidorDTO findOne(Long id);

    /**
     *  Delete the "id" seguidor.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
