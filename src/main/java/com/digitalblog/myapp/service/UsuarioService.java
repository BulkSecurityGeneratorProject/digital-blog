package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.UsuarioDTO;
import java.util.List;

/**
 * Service Interface for managing Usuario.
 */
public interface UsuarioService {

    /**
     * Save a usuario.
     *
     * @param usuarioDTO the entity to save
     * @return the persisted entity
     */
    UsuarioDTO save(UsuarioDTO usuarioDTO);

    /**
     *  Get all the usuarios.
     *  
     *  @return the list of entities
     */
    List<UsuarioDTO> findAll();

    /**
     *  Get the "id" usuario.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UsuarioDTO findOne(Long id);

    /**
     *  Delete the "id" usuario.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
