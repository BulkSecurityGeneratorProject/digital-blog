package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.ImagenPorPublicacionDTO;
import java.util.List;

/**
 * Service Interface for managing ImagenPorPublicacion.
 */
public interface ImagenPorPublicacionService {

    /**
     * Save a imagenPorPublicacion.
     *
     * @param imagenPorPublicacionDTO the entity to save
     * @return the persisted entity
     */
    ImagenPorPublicacionDTO save(ImagenPorPublicacionDTO imagenPorPublicacionDTO);

    /**
     *  Get all the imagenPorPublicacions.
     *
     *  @return the list of entities
     */
    List<ImagenPorPublicacionDTO> findAll();

    /**
     *  Get the "id" imagenPorPublicacion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ImagenPorPublicacionDTO findOne(Long id);

    /**
     *  Delete the "id" imagenPorPublicacion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
