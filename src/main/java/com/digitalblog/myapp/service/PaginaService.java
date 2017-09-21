package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.PaginaDTO;
import java.util.List;

/**
 * Service Interface for managing Pagina.
 */
public interface PaginaService {

    /**
     * Save a pagina.
     *
     * @param paginaDTO the entity to save
     * @return the persisted entity
     */
    PaginaDTO save(PaginaDTO paginaDTO);

    /**
     *  Get all the paginas.
     *  
     *  @return the list of entities
     */
    List<PaginaDTO> findAll();

    /**
     *  Get the "id" pagina.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaginaDTO findOne(Long id);

    /**
     *  Delete the "id" pagina.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
