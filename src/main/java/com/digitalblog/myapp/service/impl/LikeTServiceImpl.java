package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.LikeTService;
import com.digitalblog.myapp.domain.LikeT;
import com.digitalblog.myapp.repository.LikeTRepository;
import com.digitalblog.myapp.service.dto.LikeTDTO;
import com.digitalblog.myapp.service.mapper.LikeTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LikeT.
 */
@Service
@Transactional
public class LikeTServiceImpl implements LikeTService{

    private final Logger log = LoggerFactory.getLogger(LikeTServiceImpl.class);

    private final LikeTRepository likeTRepository;

    private final LikeTMapper likeTMapper;

    public LikeTServiceImpl(LikeTRepository likeTRepository, LikeTMapper likeTMapper) {
        this.likeTRepository = likeTRepository;
        this.likeTMapper = likeTMapper;
    }

    /**
     * Save a likeT.
     *
     * @param likeTDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LikeTDTO save(LikeTDTO likeTDTO) {
        log.debug("Request to save LikeT : {}", likeTDTO);
        LikeT likeT = likeTMapper.toEntity(likeTDTO);
        likeT = likeTRepository.save(likeT);
        return likeTMapper.toDto(likeT);
    }

    /**
     *  Get all the likeTS.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LikeTDTO> findAll() {
        log.debug("Request to get all LikeTS");
        return likeTRepository.findAll().stream()
            .map(likeTMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one likeT by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LikeTDTO findOne(Long id) {
        log.debug("Request to get LikeT : {}", id);
        LikeT likeT = likeTRepository.findOne(id);
        return likeTMapper.toDto(likeT);
    }

    /**
     *  Delete the  likeT by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LikeT : {}", id);
        likeTRepository.delete(id);
    }
}
