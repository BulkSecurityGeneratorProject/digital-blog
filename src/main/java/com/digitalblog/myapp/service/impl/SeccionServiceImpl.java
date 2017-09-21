package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.SeccionService;
import com.digitalblog.myapp.domain.Seccion;
import com.digitalblog.myapp.repository.SeccionRepository;
import com.digitalblog.myapp.service.dto.SeccionDTO;
import com.digitalblog.myapp.service.mapper.SeccionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Seccion.
 */
@Service
@Transactional
public class SeccionServiceImpl implements SeccionService{

    private final Logger log = LoggerFactory.getLogger(SeccionServiceImpl.class);
    
    private final SeccionRepository seccionRepository;

    private final SeccionMapper seccionMapper;

    public SeccionServiceImpl(SeccionRepository seccionRepository, SeccionMapper seccionMapper) {
        this.seccionRepository = seccionRepository;
        this.seccionMapper = seccionMapper;
    }

    /**
     * Save a seccion.
     *
     * @param seccionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeccionDTO save(SeccionDTO seccionDTO) {
        log.debug("Request to save Seccion : {}", seccionDTO);
        Seccion seccion = seccionMapper.seccionDTOToSeccion(seccionDTO);
        seccion = seccionRepository.save(seccion);
        SeccionDTO result = seccionMapper.seccionToSeccionDTO(seccion);
        return result;
    }

    /**
     *  Get all the seccions.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SeccionDTO> findAll() {
        log.debug("Request to get all Seccions");
        List<SeccionDTO> result = seccionRepository.findAll().stream()
            .map(seccionMapper::seccionToSeccionDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one seccion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeccionDTO findOne(Long id) {
        log.debug("Request to get Seccion : {}", id);
        Seccion seccion = seccionRepository.findOne(id);
        SeccionDTO seccionDTO = seccionMapper.seccionToSeccionDTO(seccion);
        return seccionDTO;
    }

    /**
     *  Delete the  seccion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seccion : {}", id);
        seccionRepository.delete(id);
    }
}
