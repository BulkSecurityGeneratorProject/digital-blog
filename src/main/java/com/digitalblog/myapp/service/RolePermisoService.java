package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.RolePermisoDTO;
import java.util.List;

/**
 * Service Interface for managing RolePermiso.
 */
public interface RolePermisoService {

    /**
     * Save a rolePermiso.
     *
     * @param rolePermisoDTO the entity to save
     * @return the persisted entity
     */
    RolePermisoDTO save(RolePermisoDTO rolePermisoDTO);

    /**
     *  Get all the rolePermisos.
     *  
     *  @return the list of entities
     */
    List<RolePermisoDTO> findAll();

    /**
     *  Get the "id" rolePermiso.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RolePermisoDTO findOne(Long id);

    /**
     *  Delete the "id" rolePermiso.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
