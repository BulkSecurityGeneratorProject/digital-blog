package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.BibliotecaService;
import com.digitalblog.myapp.domain.Biblioteca;
import com.digitalblog.myapp.repository.BibliotecaRepository;
import com.digitalblog.myapp.service.dto.BibliotecaDTO;
import com.digitalblog.myapp.service.mapper.BibliotecaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Biblioteca.
 */
@Service
@Transactional
public class BibliotecaServiceImpl implements BibliotecaService{

    private final Logger log = LoggerFactory.getLogger(BibliotecaServiceImpl.class);

    private final BibliotecaRepository bibliotecaRepository;

    private final BibliotecaMapper bibliotecaMapper;

    public BibliotecaServiceImpl(BibliotecaRepository bibliotecaRepository, BibliotecaMapper bibliotecaMapper) {
        this.bibliotecaRepository = bibliotecaRepository;
        this.bibliotecaMapper = bibliotecaMapper;
    }

    /**
     * Save a biblioteca.
     *
     * @param bibliotecaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BibliotecaDTO save(BibliotecaDTO bibliotecaDTO) {
        log.debug("Request to save Biblioteca : {}", bibliotecaDTO);
        Biblioteca biblioteca = bibliotecaMapper.toEntity(bibliotecaDTO);
        biblioteca = bibliotecaRepository.save(biblioteca);
        return bibliotecaMapper.toDto(biblioteca);
    }

    /**
     *  Get all the bibliotecas.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BibliotecaDTO> findAll() {
        log.debug("Request to get all Bibliotecas");
        return bibliotecaRepository.findAll().stream()
            .map(bibliotecaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one biblioteca by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BibliotecaDTO findOne(Long id) {
        log.debug("Request to get Biblioteca : {}", id);
        Biblioteca biblioteca = bibliotecaRepository.findOne(id);
        return bibliotecaMapper.toDto(biblioteca);
    }

    /**
     *  Delete the  biblioteca by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Biblioteca : {}", id);
        bibliotecaRepository.delete(id);
    }
}
