package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.PublicacionDTO;
import java.util.List;

/**
 * Service Interface for managing Publicacion.
 */
public interface PublicacionService {

    /**
     * Save a publicacion.
     *
     * @param publicacionDTO the entity to save
     * @return the persisted entity
     */
    PublicacionDTO save(PublicacionDTO publicacionDTO);

    /**
     *  Get all the publicacions.
     *
     *  @return the list of entities
     */
    List<PublicacionDTO> findAll();

    /**
     *  Get the "id" publicacion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PublicacionDTO findOne(Long id);

    /**
     *  Delete the "id" publicacion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
