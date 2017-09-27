package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.SeccionPublicacionDTO;

/**
 * Service Interface for managing SeccionPublicacion.
 */
public interface SeccionPublicacionCustomService {

   SeccionPublicacionDTO buscarPublicacionPorSeccion(Long idSeccion,Long idPublicacion);

    SeccionPublicacionDTO findByIdPS(SeccionPublicacionDTO seccionPublicacion);
}
