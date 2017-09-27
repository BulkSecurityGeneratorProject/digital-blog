package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.RolDTO;
import java.util.List;

/**
 * Service Interface for managing Rol.
 */
public interface RolService {

    /**
     * Save a rol.
     *
     * @param rolDTO the entity to save
     * @return the persisted entity
     */
    RolDTO save(RolDTO rolDTO);

    /**
     *  Get all the rols.
     *
     *  @return the list of entities
     */
    List<RolDTO> findAll();

    /**
     *  Get the "id" rol.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RolDTO findOne(Long id);

    /**
     *  Delete the "id" rol.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
