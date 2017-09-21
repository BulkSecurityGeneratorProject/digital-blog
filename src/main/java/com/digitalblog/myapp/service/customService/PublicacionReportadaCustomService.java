package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.domain.PublicacionReportada;
import com.digitalblog.myapp.service.dto.PublicacionReportadaDTO;
import com.digitalblog.myapp.service.dto.UsuarioDTO;

import java.util.List;

/**
 * Service Interface for managing PublicacionReportada.
 */
public interface PublicacionReportadaCustomService {

    /**
     * Save a publicacionReportada.
     *
     * @param id the entity to get
     * @return the persisted entity
     */
    PublicacionReportadaDTO findPublicacionByIdPublicacion(Integer id);


}
