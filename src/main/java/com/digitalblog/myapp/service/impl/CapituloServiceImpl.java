package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.CapituloService;
import com.digitalblog.myapp.domain.Capitulo;
import com.digitalblog.myapp.repository.CapituloRepository;
import com.digitalblog.myapp.service.dto.CapituloDTO;
import com.digitalblog.myapp.service.mapper.CapituloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Capitulo.
 */
@Service
@Transactional
public class CapituloServiceImpl implements CapituloService{

    private final Logger log = LoggerFactory.getLogger(CapituloServiceImpl.class);
    
    private final CapituloRepository capituloRepository;

    private final CapituloMapper capituloMapper;

    public CapituloServiceImpl(CapituloRepository capituloRepository, CapituloMapper capituloMapper) {
        this.capituloRepository = capituloRepository;
        this.capituloMapper = capituloMapper;
    }

    /**
     * Save a capitulo.
     *
     * @param capituloDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CapituloDTO save(CapituloDTO capituloDTO) {
        log.debug("Request to save Capitulo : {}", capituloDTO);
        Capitulo capitulo = capituloMapper.capituloDTOToCapitulo(capituloDTO);
        capitulo = capituloRepository.save(capitulo);
        CapituloDTO result = capituloMapper.capituloToCapituloDTO(capitulo);
        return result;
    }

    /**
     *  Get all the capitulos.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CapituloDTO> findAll() {
        log.debug("Request to get all Capitulos");
        List<CapituloDTO> result = capituloRepository.findAll().stream()
            .map(capituloMapper::capituloToCapituloDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one capitulo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CapituloDTO findOne(Long id) {
        log.debug("Request to get Capitulo : {}", id);
        Capitulo capitulo = capituloRepository.findOne(id);
        CapituloDTO capituloDTO = capituloMapper.capituloToCapituloDTO(capitulo);
        return capituloDTO;
    }

    /**
     *  Delete the  capitulo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Capitulo : {}", id);
        capituloRepository.delete(id);
    }
}
