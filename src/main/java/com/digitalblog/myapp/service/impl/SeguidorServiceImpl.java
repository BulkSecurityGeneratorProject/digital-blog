package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.SeguidorService;
import com.digitalblog.myapp.domain.Seguidor;
import com.digitalblog.myapp.repository.SeguidorRepository;
import com.digitalblog.myapp.service.dto.SeguidorDTO;
import com.digitalblog.myapp.service.mapper.SeguidorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Seguidor.
 */
@Service
@Transactional
public class SeguidorServiceImpl implements SeguidorService{

    private final Logger log = LoggerFactory.getLogger(SeguidorServiceImpl.class);

    private final SeguidorRepository seguidorRepository;

    private final SeguidorMapper seguidorMapper;

    public SeguidorServiceImpl(SeguidorRepository seguidorRepository, SeguidorMapper seguidorMapper) {
        this.seguidorRepository = seguidorRepository;
        this.seguidorMapper = seguidorMapper;
    }

    /**
     * Save a seguidor.
     *
     * @param seguidorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeguidorDTO save(SeguidorDTO seguidorDTO) {
        log.debug("Request to save Seguidor : {}", seguidorDTO);
        Seguidor seguidor = seguidorMapper.toEntity(seguidorDTO);
        seguidor = seguidorRepository.save(seguidor);
        return seguidorMapper.toDto(seguidor);
    }

    /**
     *  Get all the seguidors.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SeguidorDTO> findAll() {
        log.debug("Request to get all Seguidors");
        return seguidorRepository.findAll().stream()
            .map(seguidorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one seguidor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeguidorDTO findOne(Long id) {
        log.debug("Request to get Seguidor : {}", id);
        Seguidor seguidor = seguidorRepository.findOne(id);
        return seguidorMapper.toDto(seguidor);
    }

    /**
     *  Delete the  seguidor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seguidor : {}", id);
        seguidorRepository.delete(id);
    }
}
