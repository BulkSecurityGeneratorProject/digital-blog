package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.ComentarioDTO;
import java.util.List;

/**
 * Service Interface for managing Comentario.
 */
public interface ComentarioService {

    /**
     * Save a comentario.
     *
     * @param comentarioDTO the entity to save
     * @return the persisted entity
     */
    ComentarioDTO save(ComentarioDTO comentarioDTO);

    /**
     *  Get all the comentarios.
     *  
     *  @return the list of entities
     */
    List<ComentarioDTO> findAll();

    /**
     *  Get the "id" comentario.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ComentarioDTO findOne(Long id);

    /**
     *  Delete the "id" comentario.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
