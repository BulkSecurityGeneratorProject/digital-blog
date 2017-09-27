package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.CoolaboradorDTO;
import java.util.List;

/**
 * Service Interface for managing Coolaborador.
 */
public interface CoolaboradorService {

    /**
     * Save a coolaborador.
     *
     * @param coolaboradorDTO the entity to save
     * @return the persisted entity
     */
    CoolaboradorDTO save(CoolaboradorDTO coolaboradorDTO);

    /**
     *  Get all the coolaboradors.
     *
     *  @return the list of entities
     */
    List<CoolaboradorDTO> findAll();

    /**
     *  Get the "id" coolaborador.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CoolaboradorDTO findOne(Long id);

    /**
     *  Delete the "id" coolaborador.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
