package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.BibliotecaDTO;
import com.digitalblog.myapp.service.dto.PublicacionDTO;

/**
 * Service Interface for managing Biblioteca.
 */
public interface BibliotecaCustomService {

    /**
     * Buscar la biblioteca por el id del jhi-user.
     *
     * @param id para buscar la biblioteca
     *
     */
    BibliotecaDTO buscarBibliotecaPorJhiUser(Long id);

    PublicacionDTO savePublicacion(PublicacionDTO publicacionDTO);
}
