package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.NotificacionDTO;
import java.util.List;

/**
 * Service Interface for managing Notificacion.
 */
public interface NotificacionService {

    /**
     * Save a notificacion.
     *
     * @param notificacionDTO the entity to save
     * @return the persisted entity
     */
    NotificacionDTO save(NotificacionDTO notificacionDTO);

    /**
     *  Get all the notificacions.
     *  
     *  @return the list of entities
     */
    List<NotificacionDTO> findAll();

    /**
     *  Get the "id" notificacion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NotificacionDTO findOne(Long id);

    /**
     *  Delete the "id" notificacion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
