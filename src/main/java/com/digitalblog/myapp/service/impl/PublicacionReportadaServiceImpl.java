package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.PublicacionReportadaService;
import com.digitalblog.myapp.domain.PublicacionReportada;
import com.digitalblog.myapp.repository.PublicacionReportadaRepository;
import com.digitalblog.myapp.service.dto.PublicacionReportadaDTO;
import com.digitalblog.myapp.service.mapper.PublicacionReportadaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PublicacionReportada.
 */
@Service
@Transactional
public class PublicacionReportadaServiceImpl implements PublicacionReportadaService{

    private final Logger log = LoggerFactory.getLogger(PublicacionReportadaServiceImpl.class);
    
    private final PublicacionReportadaRepository publicacionReportadaRepository;

    private final PublicacionReportadaMapper publicacionReportadaMapper;

    public PublicacionReportadaServiceImpl(PublicacionReportadaRepository publicacionReportadaRepository, PublicacionReportadaMapper publicacionReportadaMapper) {
        this.publicacionReportadaRepository = publicacionReportadaRepository;
        this.publicacionReportadaMapper = publicacionReportadaMapper;
    }

    /**
     * Save a publicacionReportada.
     *
     * @param publicacionReportadaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PublicacionReportadaDTO save(PublicacionReportadaDTO publicacionReportadaDTO) {
        log.debug("Request to save PublicacionReportada : {}", publicacionReportadaDTO);
        PublicacionReportada publicacionReportada = publicacionReportadaMapper.publicacionReportadaDTOToPublicacionReportada(publicacionReportadaDTO);
        publicacionReportada = publicacionReportadaRepository.save(publicacionReportada);
        PublicacionReportadaDTO result = publicacionReportadaMapper.publicacionReportadaToPublicacionReportadaDTO(publicacionReportada);
        return result;
    }

    /**
     *  Get all the publicacionReportadas.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PublicacionReportadaDTO> findAll() {
        log.debug("Request to get all PublicacionReportadas");
        List<PublicacionReportadaDTO> result = publicacionReportadaRepository.findAll().stream()
            .map(publicacionReportadaMapper::publicacionReportadaToPublicacionReportadaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one publicacionReportada by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PublicacionReportadaDTO findOne(Long id) {
        log.debug("Request to get PublicacionReportada : {}", id);
        PublicacionReportada publicacionReportada = publicacionReportadaRepository.findOne(id);
        PublicacionReportadaDTO publicacionReportadaDTO = publicacionReportadaMapper.publicacionReportadaToPublicacionReportadaDTO(publicacionReportada);
        return publicacionReportadaDTO;
    }

    /**
     *  Delete the  publicacionReportada by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PublicacionReportada : {}", id);
        publicacionReportadaRepository.delete(id);
    }
}
