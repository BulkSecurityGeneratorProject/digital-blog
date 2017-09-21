package com.digitalblog.myapp.service;

import com.digitalblog.myapp.service.dto.LikeTDTO;
import java.util.List;

/**
 * Service Interface for managing LikeT.
 */
public interface LikeTService {

    /**
     * Save a likeT.
     *
     * @param likeTDTO the entity to save
     * @return the persisted entity
     */
    LikeTDTO save(LikeTDTO likeTDTO);

    /**
     *  Get all the likeTS.
     *  
     *  @return the list of entities
     */
    List<LikeTDTO> findAll();

    /**
     *  Get the "id" likeT.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LikeTDTO findOne(Long id);

    /**
     *  Delete the "id" likeT.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
