package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.SeccionDTO;

import java.util.ArrayList;

/**
 * Service Interface for managing Seccion.
 */
public interface SeccionCustomService {

    /**
     * Save a seccion.
     *
     * @param id the entity to save
     * @return the persisted entity
     */
    ArrayList<SeccionDTO> buscarSeccionPorBiblioteca(Long id);

    SeccionDTO buscarSeccionPorNombre(String nombre,Long id);

    /**
     * findSeccionByNameById
     * @param idBiblioteca
     * @param nombreSeccion
     * @return SeccionDTO
     */
    SeccionDTO findSeccionByNameById(Long idBiblioteca, String nombreSeccion);
}
