package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.CapituloDTO;

import java.util.ArrayList;

/**
 * Service Interface for managing Publicacion.
 */
public interface CapituloCustomService {

    /**
     * Nuevos métodos agregados
     */
    ArrayList<CapituloDTO> buscarCapituloPorPublicacion(Long id);

    CapituloDTO obtenerUltimoCapituloPublicacion(ArrayList<CapituloDTO> capitulos, CapituloDTO ultimoCapitulo);

}
