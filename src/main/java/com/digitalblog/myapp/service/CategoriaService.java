package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.CategoriaDTO;
import java.util.List;

/**
 * Service Interface for managing Categoria.
 */
public interface CategoriaService {

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save
     * @return the persisted entity
     */
    CategoriaDTO save(CategoriaDTO categoriaDTO);

    /**
     *  Get all the categorias.
     *
     *  @return the list of entities
     */
    List<CategoriaDTO> findAll();

    /**
     *  Get the "id" categoria.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CategoriaDTO findOne(Long id);

    /**
     *  Delete the "id" categoria.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
