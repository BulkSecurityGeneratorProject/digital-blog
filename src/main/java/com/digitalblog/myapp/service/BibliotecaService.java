package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.BibliotecaDTO;
import java.util.List;

/**
 * Service Interface for managing Biblioteca.
 */
public interface BibliotecaService {

    /**
     * Save a biblioteca.
     *
     * @param bibliotecaDTO the entity to save
     * @return the persisted entity
     */
    BibliotecaDTO save(BibliotecaDTO bibliotecaDTO);

    /**
     *  Get all the bibliotecas.
     *
     *  @return the list of entities
     */
    List<BibliotecaDTO> findAll();

    /**
     *  Get the "id" biblioteca.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BibliotecaDTO findOne(Long id);

    /**
     *  Delete the "id" biblioteca.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
