package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.NotaDTO;
import java.util.List;

/**
 * Service Interface for managing Nota.
 */
public interface NotaService {

    /**
     * Save a nota.
     *
     * @param notaDTO the entity to save
     * @return the persisted entity
     */
    NotaDTO save(NotaDTO notaDTO);

    /**
     *  Get all the notas.
     *
     *  @return the list of entities
     */
    List<NotaDTO> findAll();

    /**
     *  Get the "id" nota.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NotaDTO findOne(Long id);

    /**
     *  Delete the "id" nota.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
