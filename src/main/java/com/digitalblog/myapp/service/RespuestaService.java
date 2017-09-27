package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.RespuestaDTO;
import java.util.List;

/**
 * Service Interface for managing Respuesta.
 */
public interface RespuestaService {

    /**
     * Save a respuesta.
     *
     * @param respuestaDTO the entity to save
     * @return the persisted entity
     */
    RespuestaDTO save(RespuestaDTO respuestaDTO);

    /**
     *  Get all the respuestas.
     *
     *  @return the list of entities
     */
    List<RespuestaDTO> findAll();

    /**
     *  Get the "id" respuesta.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RespuestaDTO findOne(Long id);

    /**
     *  Delete the "id" respuesta.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
