package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.LikeTService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.LikeTDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LikeT.
 */
@RestController
@RequestMapping("/api")
public class LikeTResource {

    private final Logger log = LoggerFactory.getLogger(LikeTResource.class);

    private static final String ENTITY_NAME = "likeT";

    private final LikeTService likeTService;

    public LikeTResource(LikeTService likeTService) {
        this.likeTService = likeTService;
    }

    /**
     * POST  /like-ts : Create a new likeT.
     *
     * @param likeTDTO the likeTDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new likeTDTO, or with status 400 (Bad Request) if the likeT has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/like-ts")
    @Timed
    public ResponseEntity<LikeTDTO> createLikeT(@RequestBody LikeTDTO likeTDTO) throws URISyntaxException {
        log.debug("REST request to save LikeT : {}", likeTDTO);
        if (likeTDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new likeT cannot already have an ID")).body(null);
        }
        LikeTDTO result = likeTService.save(likeTDTO);
        return ResponseEntity.created(new URI("/api/like-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /like-ts : Updates an existing likeT.
     *
     * @param likeTDTO the likeTDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated likeTDTO,
     * or with status 400 (Bad Request) if the likeTDTO is not valid,
     * or with status 500 (Internal Server Error) if the likeTDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/like-ts")
    @Timed
    public ResponseEntity<LikeTDTO> updateLikeT(@RequestBody LikeTDTO likeTDTO) throws URISyntaxException {
        log.debug("REST request to update LikeT : {}", likeTDTO);
        if (likeTDTO.getId() == null) {
            return createLikeT(likeTDTO);
        }
        LikeTDTO result = likeTService.save(likeTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, likeTDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /like-ts : get all the likeTS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of likeTS in body
     */
    @GetMapping("/like-ts")
    @Timed
    public List<LikeTDTO> getAllLikeTS() {
        log.debug("REST request to get all LikeTS");
        return likeTService.findAll();
        }

    /**
     * GET  /like-ts/:id : get the "id" likeT.
     *
     * @param id the id of the likeTDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the likeTDTO, or with status 404 (Not Found)
     */
    @GetMapping("/like-ts/{id}")
    @Timed
    public ResponseEntity<LikeTDTO> getLikeT(@PathVariable Long id) {
        log.debug("REST request to get LikeT : {}", id);
        LikeTDTO likeTDTO = likeTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(likeTDTO));
    }

    /**
     * DELETE  /like-ts/:id : delete the "id" likeT.
     *
     * @param id the id of the likeTDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/like-ts/{id}")
    @Timed
    public ResponseEntity<Void> deleteLikeT(@PathVariable Long id) {
        log.debug("REST request to delete LikeT : {}", id);
        likeTService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
