package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.TemaService;
import com.digitalblog.myapp.domain.Tema;
import com.digitalblog.myapp.repository.TemaRepository;
import com.digitalblog.myapp.service.dto.TemaDTO;
import com.digitalblog.myapp.service.mapper.TemaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Tema.
 */
@Service
@Transactional
public class TemaServiceImpl implements TemaService{

    private final Logger log = LoggerFactory.getLogger(TemaServiceImpl.class);

    private final TemaRepository temaRepository;

    private final TemaMapper temaMapper;

    public TemaServiceImpl(TemaRepository temaRepository, TemaMapper temaMapper) {
        this.temaRepository = temaRepository;
        this.temaMapper = temaMapper;
    }

    /**
     * Save a tema.
     *
     * @param temaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TemaDTO save(TemaDTO temaDTO) {
        log.debug("Request to save Tema : {}", temaDTO);
        Tema tema = temaMapper.toEntity(temaDTO);
        tema = temaRepository.save(tema);
        return temaMapper.toDto(tema);
    }

    /**
     *  Get all the temas.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TemaDTO> findAll() {
        log.debug("Request to get all Temas");
        return temaRepository.findAll().stream()
            .map(temaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one tema by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TemaDTO findOne(Long id) {
        log.debug("Request to get Tema : {}", id);
        Tema tema = temaRepository.findOne(id);
        return temaMapper.toDto(tema);
    }

    /**
     *  Delete the  tema by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tema : {}", id);
        temaRepository.delete(id);
    }
}
