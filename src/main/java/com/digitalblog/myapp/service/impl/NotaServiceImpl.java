package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.NotaService;
import com.digitalblog.myapp.domain.Nota;
import com.digitalblog.myapp.repository.NotaRepository;
import com.digitalblog.myapp.service.dto.NotaDTO;
import com.digitalblog.myapp.service.mapper.NotaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Nota.
 */
@Service
@Transactional
public class NotaServiceImpl implements NotaService{

    private final Logger log = LoggerFactory.getLogger(NotaServiceImpl.class);
    
    private final NotaRepository notaRepository;

    private final NotaMapper notaMapper;

    public NotaServiceImpl(NotaRepository notaRepository, NotaMapper notaMapper) {
        this.notaRepository = notaRepository;
        this.notaMapper = notaMapper;
    }

    /**
     * Save a nota.
     *
     * @param notaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NotaDTO save(NotaDTO notaDTO) {
        log.debug("Request to save Nota : {}", notaDTO);
        Nota nota = notaMapper.notaDTOToNota(notaDTO);
        nota = notaRepository.save(nota);
        NotaDTO result = notaMapper.notaToNotaDTO(nota);
        return result;
    }

    /**
     *  Get all the notas.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NotaDTO> findAll() {
        log.debug("Request to get all Notas");
        List<NotaDTO> result = notaRepository.findAll().stream()
            .map(notaMapper::notaToNotaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one nota by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NotaDTO findOne(Long id) {
        log.debug("Request to get Nota : {}", id);
        Nota nota = notaRepository.findOne(id);
        NotaDTO notaDTO = notaMapper.notaToNotaDTO(nota);
        return notaDTO;
    }

    /**
     *  Delete the  nota by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nota : {}", id);
        notaRepository.delete(id);
    }
}
