package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.CanalDTO;
import java.util.List;

/**
 * Service Interface for managing Canal.
 */
public interface CanalService {

    /**
     * Save a canal.
     *
     * @param canalDTO the entity to save
     * @return the persisted entity
     */
    CanalDTO save(CanalDTO canalDTO);

    /**
     *  Get all the canals.
     *  
     *  @return the list of entities
     */
    List<CanalDTO> findAll();

    /**
     *  Get the "id" canal.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CanalDTO findOne(Long id);

    /**
     *  Delete the "id" canal.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
