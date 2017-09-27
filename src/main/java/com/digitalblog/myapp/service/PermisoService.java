package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.PermisoDTO;
import java.util.List;

/**
 * Service Interface for managing Permiso.
 */
public interface PermisoService {

    /**
     * Save a permiso.
     *
     * @param permisoDTO the entity to save
     * @return the persisted entity
     */
    PermisoDTO save(PermisoDTO permisoDTO);

    /**
     *  Get all the permisos.
     *  
     *  @return the list of entities
     */
    List<PermisoDTO> findAll();

    /**
     *  Get the "id" permiso.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PermisoDTO findOne(Long id);

    /**
     *  Delete the "id" permiso.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
