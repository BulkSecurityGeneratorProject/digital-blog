package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.Biblioteca;
import com.digitalblog.myapp.domain.Publicacion;
import com.digitalblog.myapp.repository.PublicacionRepository;
import com.digitalblog.myapp.repository.customRepository.BibliotecaRepositoryCustom;
import com.digitalblog.myapp.service.customService.BibliotecaCustomService;
import com.digitalblog.myapp.service.dto.BibliotecaDTO;
import com.digitalblog.myapp.service.dto.PublicacionDTO;
import com.digitalblog.myapp.service.mapper.BibliotecaMapper;
import com.digitalblog.myapp.service.mapper.PublicacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Biblioteca.
 */
@Service
@Transactional
public class BibliotecaCustomServiceImpl implements BibliotecaCustomService {

    private final Logger log = LoggerFactory.getLogger(BibliotecaCustomServiceImpl.class);

    private final BibliotecaMapper bibliotecaMapper;

    private final BibliotecaRepositoryCustom bibliotecaRepositoryCustom;

    private final PublicacionRepository publicacionRepository;

    private final PublicacionMapper publicacionMapper;

    public BibliotecaCustomServiceImpl(BibliotecaMapper bibliotecaMapper, BibliotecaRepositoryCustom bibliotecaRepositoryCustom, PublicacionRepository publicacionRepository, PublicacionMapper publicacionMapper) {
        this.bibliotecaMapper = bibliotecaMapper;
        this.bibliotecaRepositoryCustom = bibliotecaRepositoryCustom;
        this.publicacionRepository = publicacionRepository;
        this.publicacionMapper = publicacionMapper;
    }

    /**
     * @author Eduardo Guerrero
     * Busca la biblioteca por el id del jhi-user
     * @param id para buscar la biblioteca
     * @return BibliotecaDTO
     * @version 1.0
     */
    @Transactional(readOnly = true)
    public BibliotecaDTO buscarBibliotecaPorJhiUser(Long id) {
       Biblioteca biblioteca = bibliotecaRepositoryCustom.findBibliotecaByIdJhiUser(id);
       BibliotecaDTO bibliotecaDTO = bibliotecaMapper.bibliotecaToBibliotecaDTO(biblioteca);
       return bibliotecaDTO;
    }
    /**
     * @author Eduardo Guerrero
     * Save a publicacion.
     * @param publicacionDTO the entity to save
     * @return the persisted entity
     * @version 1.0
     */
    @Override
    public PublicacionDTO savePublicacion(PublicacionDTO publicacionDTO) {
        log.debug("Request to save Publicacion : {}", publicacionDTO);
        Publicacion publicacion = publicacionMapper.publicacionDTOToPublicacion(publicacionDTO);
        publicacion = publicacionRepository.save(publicacion);
        PublicacionDTO result = publicacionMapper.publicacionToPublicacionDTO(publicacion);

        return result;
    }
}
