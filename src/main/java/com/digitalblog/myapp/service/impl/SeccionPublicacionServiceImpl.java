package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.SeccionPublicacionService;
import com.digitalblog.myapp.domain.SeccionPublicacion;
import com.digitalblog.myapp.repository.SeccionPublicacionRepository;
import com.digitalblog.myapp.service.dto.SeccionPublicacionDTO;
import com.digitalblog.myapp.service.mapper.SeccionPublicacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SeccionPublicacion.
 */
@Service
@Transactional
public class SeccionPublicacionServiceImpl implements SeccionPublicacionService{

    private final Logger log = LoggerFactory.getLogger(SeccionPublicacionServiceImpl.class);
    
    private final SeccionPublicacionRepository seccionPublicacionRepository;

    private final SeccionPublicacionMapper seccionPublicacionMapper;

    public SeccionPublicacionServiceImpl(SeccionPublicacionRepository seccionPublicacionRepository, SeccionPublicacionMapper seccionPublicacionMapper) {
        this.seccionPublicacionRepository = seccionPublicacionRepository;
        this.seccionPublicacionMapper = seccionPublicacionMapper;
    }

    /**
     * Save a seccionPublicacion.
     *
     * @param seccionPublicacionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeccionPublicacionDTO save(SeccionPublicacionDTO seccionPublicacionDTO) {
        log.debug("Request to save SeccionPublicacion : {}", seccionPublicacionDTO);
        SeccionPublicacion seccionPublicacion = seccionPublicacionMapper.seccionPublicacionDTOToSeccionPublicacion(seccionPublicacionDTO);
        seccionPublicacion = seccionPublicacionRepository.save(seccionPublicacion);
        SeccionPublicacionDTO result = seccionPublicacionMapper.seccionPublicacionToSeccionPublicacionDTO(seccionPublicacion);
        return result;
    }

    /**
     *  Get all the seccionPublicacions.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SeccionPublicacionDTO> findAll() {
        log.debug("Request to get all SeccionPublicacions");
        List<SeccionPublicacionDTO> result = seccionPublicacionRepository.findAll().stream()
            .map(seccionPublicacionMapper::seccionPublicacionToSeccionPublicacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one seccionPublicacion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeccionPublicacionDTO findOne(Long id) {
        log.debug("Request to get SeccionPublicacion : {}", id);
        SeccionPublicacion seccionPublicacion = seccionPublicacionRepository.findOne(id);
        SeccionPublicacionDTO seccionPublicacionDTO = seccionPublicacionMapper.seccionPublicacionToSeccionPublicacionDTO(seccionPublicacion);
        return seccionPublicacionDTO;
    }

    /**
     *  Delete the  seccionPublicacion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeccionPublicacion : {}", id);
        seccionPublicacionRepository.delete(id);
    }
}
