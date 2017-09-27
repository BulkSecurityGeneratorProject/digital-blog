package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.ComentarioService;
import com.digitalblog.myapp.domain.Comentario;
import com.digitalblog.myapp.repository.ComentarioRepository;
import com.digitalblog.myapp.service.dto.ComentarioDTO;
import com.digitalblog.myapp.service.mapper.ComentarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Comentario.
 */
@Service
@Transactional
public class ComentarioServiceImpl implements ComentarioService{

    private final Logger log = LoggerFactory.getLogger(ComentarioServiceImpl.class);

    private final ComentarioRepository comentarioRepository;

    private final ComentarioMapper comentarioMapper;

    public ComentarioServiceImpl(ComentarioRepository comentarioRepository, ComentarioMapper comentarioMapper) {
        this.comentarioRepository = comentarioRepository;
        this.comentarioMapper = comentarioMapper;
    }

    /**
     * Save a comentario.
     *
     * @param comentarioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ComentarioDTO save(ComentarioDTO comentarioDTO) {
        log.debug("Request to save Comentario : {}", comentarioDTO);
        Comentario comentario = comentarioMapper.toEntity(comentarioDTO);
        comentario = comentarioRepository.save(comentario);
        return comentarioMapper.toDto(comentario);
    }

    /**
     *  Get all the comentarios.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ComentarioDTO> findAll() {
        log.debug("Request to get all Comentarios");
        return comentarioRepository.findAll().stream()
            .map(comentarioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one comentario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ComentarioDTO findOne(Long id) {
        log.debug("Request to get Comentario : {}", id);
        Comentario comentario = comentarioRepository.findOne(id);
        return comentarioMapper.toDto(comentario);
    }

    /**
     *  Delete the  comentario by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comentario : {}", id);
        comentarioRepository.delete(id);
    }
}
