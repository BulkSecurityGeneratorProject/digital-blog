package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.SeccionPublicacion;
import com.digitalblog.myapp.repository.customRepository.SeccionPublicacionRepositoryCustom;
import com.digitalblog.myapp.service.customService.SeccionPublicacionCustomService;
import com.digitalblog.myapp.service.dto.SeccionPublicacionDTO;
import com.digitalblog.myapp.service.mapper.SeccionPublicacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Service Implementation for managing SeccionPublicacion.
 */
@Service
@Transactional
public class SeccionPublicacionCustomServiceImpl implements SeccionPublicacionCustomService{

    private final Logger log = LoggerFactory.getLogger(SeccionPublicacionCustomServiceImpl.class);

    private final SeccionPublicacionRepositoryCustom seccionPublicacionRepositoryCustom;

    private final SeccionPublicacionMapper seccionPublicacionMapper;

    public SeccionPublicacionCustomServiceImpl(SeccionPublicacionRepositoryCustom seccionPublicacionRepositoryCustom, SeccionPublicacionMapper seccionPublicacionMapper) {
        this.seccionPublicacionRepositoryCustom = seccionPublicacionRepositoryCustom;
        this.seccionPublicacionMapper = seccionPublicacionMapper;

    }

    /**
     * @author Eduardo Guerrero
     * se trae todas las publicaciones de esa seccion
     * @param idSeccion
     * @param idPublicacion
     * @return SeccionPublicacionDTO
     * @version 1.0
     */
    @Override
    public SeccionPublicacionDTO buscarPublicacionPorSeccion(@PathVariable Long idSeccion,@PathVariable Long idPublicacion) {
        SeccionPublicacion seccionPublicacion = seccionPublicacionRepositoryCustom.findPublicacionBySeccion(idSeccion,idPublicacion);
        SeccionPublicacionDTO seccionPublicacionDTO = seccionPublicacionMapper.seccionPublicacionToSeccionPublicacionDTO(seccionPublicacion);
        return seccionPublicacionDTO;
    }

    /**
     * @author Maureen Leon
     * @param seccionPublicacion
     * @return SeccionPublicacionDTO
     * @version 1.0
     */
    @Override
    public SeccionPublicacionDTO findByIdPS(@PathVariable SeccionPublicacionDTO seccionPublicacion) {
        return buscarPublicacionPorSeccion(seccionPublicacion.getIdSeccionSPId(),seccionPublicacion.getIdPublicacionSPId());
    }
}
