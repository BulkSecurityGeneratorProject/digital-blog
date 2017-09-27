package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.CanalService;
import com.digitalblog.myapp.domain.Canal;
import com.digitalblog.myapp.repository.CanalRepository;
import com.digitalblog.myapp.service.dto.CanalDTO;
import com.digitalblog.myapp.service.mapper.CanalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Canal.
 */
@Service
@Transactional
public class CanalServiceImpl implements CanalService{

    private final Logger log = LoggerFactory.getLogger(CanalServiceImpl.class);
    
    private final CanalRepository canalRepository;

    private final CanalMapper canalMapper;

    public CanalServiceImpl(CanalRepository canalRepository, CanalMapper canalMapper) {
        this.canalRepository = canalRepository;
        this.canalMapper = canalMapper;
    }

    /**
     * Save a canal.
     *
     * @param canalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CanalDTO save(CanalDTO canalDTO) {
        log.debug("Request to save Canal : {}", canalDTO);
        Canal canal = canalMapper.canalDTOToCanal(canalDTO);
        canal = canalRepository.save(canal);
        CanalDTO result = canalMapper.canalToCanalDTO(canal);
        return result;
    }

    /**
     *  Get all the canals.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CanalDTO> findAll() {
        log.debug("Request to get all Canals");
        List<CanalDTO> result = canalRepository.findAll().stream()
            .map(canalMapper::canalToCanalDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one canal by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CanalDTO findOne(Long id) {
        log.debug("Request to get Canal : {}", id);
        Canal canal = canalRepository.findOne(id);
        CanalDTO canalDTO = canalMapper.canalToCanalDTO(canal);
        return canalDTO;
    }

    /**
     *  Delete the  canal by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Canal : {}", id);
        canalRepository.delete(id);
    }
}
