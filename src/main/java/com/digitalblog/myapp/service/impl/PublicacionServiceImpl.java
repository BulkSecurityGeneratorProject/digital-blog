package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.PublicacionService;
import com.digitalblog.myapp.domain.Publicacion;
import com.digitalblog.myapp.repository.PublicacionRepository;
import com.digitalblog.myapp.service.dto.PublicacionDTO;
import com.digitalblog.myapp.service.mapper.PublicacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Publicacion.
 */
@Service
@Transactional
public class PublicacionServiceImpl implements PublicacionService{

    private final Logger log = LoggerFactory.getLogger(PublicacionServiceImpl.class);

    private final PublicacionRepository publicacionRepository;

    private final PublicacionMapper publicacionMapper;

    public PublicacionServiceImpl(PublicacionRepository publicacionRepository, PublicacionMapper publicacionMapper) {
        this.publicacionRepository = publicacionRepository;
        this.publicacionMapper = publicacionMapper;
    }

    /**
     * Save a publicacion.
     *
     * @param publicacionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PublicacionDTO save(PublicacionDTO publicacionDTO) {
        log.debug("Request to save Publicacion : {}", publicacionDTO);
        Publicacion publicacion = publicacionMapper.toEntity(publicacionDTO);
        publicacion = publicacionRepository.save(publicacion);
        return publicacionMapper.toDto(publicacion);
    }

    /**
     *  Get all the publicacions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PublicacionDTO> findAll() {
        log.debug("Request to get all Publicacions");
        return publicacionRepository.findAll().stream()
            .map(publicacionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one publicacion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PublicacionDTO findOne(Long id) {
        log.debug("Request to get Publicacion : {}", id);
        Publicacion publicacion = publicacionRepository.findOne(id);
        return publicacionMapper.toDto(publicacion);
    }

    /**
     *  Delete the  publicacion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Publicacion : {}", id);
        publicacionRepository.delete(id);
    }
}
