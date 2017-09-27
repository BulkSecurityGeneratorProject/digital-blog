package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.PublicacionReportada;
import com.digitalblog.myapp.domain.Usuario;
import com.digitalblog.myapp.repository.customRepository.PublicacionReportadaRepositoryCustom;
import com.digitalblog.myapp.service.customService.PublicacionReportadaCustomService;
import com.digitalblog.myapp.service.dto.PublicacionReportadaDTO;
import com.digitalblog.myapp.service.dto.UsuarioDTO;
import com.digitalblog.myapp.service.mapper.PublicacionReportadaMapper;
import com.digitalblog.myapp.service.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing PublicacionReportada.
 */
@Service
@Transactional
public class PublicacionReportadaCustomServiceImpl implements PublicacionReportadaCustomService {

    private final Logger log = LoggerFactory.getLogger(PublicacionReportadaCustomServiceImpl.class);

    private final PublicacionReportadaRepositoryCustom publicacionReportadaRepositoryCustom;

    private final PublicacionReportadaMapper publicacionReportadaMapper;

    private final UsuarioMapper usuarioMapper;

    public PublicacionReportadaCustomServiceImpl(PublicacionReportadaRepositoryCustom publicacionReportadaRepositoryCustom, PublicacionReportadaMapper publicacionReportadaMapper, UsuarioMapper usuarioMapper) {
        this.publicacionReportadaRepositoryCustom = publicacionReportadaRepositoryCustom;
        this.publicacionReportadaMapper = publicacionReportadaMapper;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * @author Eduardo Guerrero
     * Encuentra las publicacion reportada por el id de la publicacion
     * @param id the entity to get
     * @return La publicacion reportada
     * @version 1.0
     */
    @Override
    @Transactional(readOnly = true)
    public PublicacionReportadaDTO findPublicacionByIdPublicacion(Integer id) {
        PublicacionReportada publicacionReportada = publicacionReportadaRepositoryCustom.findPublicacionPorIdPublicacion(id);
        PublicacionReportadaDTO publicacionReportadaDTO = publicacionReportadaMapper.publicacionReportadaToPublicacionReportadaDTO(publicacionReportada);
        return publicacionReportadaDTO;
    }

}
