package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.SuscripcionesService;
import com.digitalblog.myapp.domain.Suscripciones;
import com.digitalblog.myapp.repository.SuscripcionesRepository;
import com.digitalblog.myapp.service.dto.SuscripcionesDTO;
import com.digitalblog.myapp.service.mapper.SuscripcionesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Suscripciones.
 */
@Service
@Transactional
public class SuscripcionesServiceImpl implements SuscripcionesService{

    private final Logger log = LoggerFactory.getLogger(SuscripcionesServiceImpl.class);
    
    private final SuscripcionesRepository suscripcionesRepository;

    private final SuscripcionesMapper suscripcionesMapper;

    public SuscripcionesServiceImpl(SuscripcionesRepository suscripcionesRepository, SuscripcionesMapper suscripcionesMapper) {
        this.suscripcionesRepository = suscripcionesRepository;
        this.suscripcionesMapper = suscripcionesMapper;
    }

    /**
     * Save a suscripciones.
     *
     * @param suscripcionesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SuscripcionesDTO save(SuscripcionesDTO suscripcionesDTO) {
        log.debug("Request to save Suscripciones : {}", suscripcionesDTO);
        Suscripciones suscripciones = suscripcionesMapper.suscripcionesDTOToSuscripciones(suscripcionesDTO);
        suscripciones = suscripcionesRepository.save(suscripciones);
        SuscripcionesDTO result = suscripcionesMapper.suscripcionesToSuscripcionesDTO(suscripciones);
        return result;
    }

    /**
     *  Get all the suscripciones.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionesDTO> findAll() {
        log.debug("Request to get all Suscripciones");
        List<SuscripcionesDTO> result = suscripcionesRepository.findAll().stream()
            .map(suscripcionesMapper::suscripcionesToSuscripcionesDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one suscripciones by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SuscripcionesDTO findOne(Long id) {
        log.debug("Request to get Suscripciones : {}", id);
        Suscripciones suscripciones = suscripcionesRepository.findOne(id);
        SuscripcionesDTO suscripcionesDTO = suscripcionesMapper.suscripcionesToSuscripcionesDTO(suscripciones);
        return suscripcionesDTO;
    }

    /**
     *  Delete the  suscripciones by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Suscripciones : {}", id);
        suscripcionesRepository.delete(id);
    }
}
