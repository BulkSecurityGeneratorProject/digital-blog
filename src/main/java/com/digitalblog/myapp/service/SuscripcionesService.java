package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.SuscripcionesDTO;
import java.util.List;

/**
 * Service Interface for managing Suscripciones.
 */
public interface SuscripcionesService {

    /**
     * Save a suscripciones.
     *
     * @param suscripcionesDTO the entity to save
     * @return the persisted entity
     */
    SuscripcionesDTO save(SuscripcionesDTO suscripcionesDTO);

    /**
     *  Get all the suscripciones.
     *
     *  @return the list of entities
     */
    List<SuscripcionesDTO> findAll();

    /**
     *  Get the "id" suscripciones.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SuscripcionesDTO findOne(Long id);

    /**
     *  Delete the "id" suscripciones.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
