package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.PaginaDTO;

import java.util.ArrayList;

/**
 * Service Interface for managing Publicacion.
 */
public interface PaginaCustomService {

    /**
     * Nuevos métodos agregados
     */

    ArrayList<PaginaDTO> buscarPaginasPorCapitulo(Long id);

    PaginaDTO obtenerUltimaPaginaCapitulo(ArrayList<PaginaDTO> paginas, PaginaDTO ultimaPagina);

    PaginaDTO obtenerPaginaAnterior(Integer numPagina, Long idCapitulo);
}
