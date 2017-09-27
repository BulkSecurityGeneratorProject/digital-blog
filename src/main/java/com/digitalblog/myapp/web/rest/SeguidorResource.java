package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.SeguidorService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.SeguidorDTO;
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
 * REST controller for managing Seguidor.
 */
@RestController
@RequestMapping("/api")
public class SeguidorResource {

    private final Logger log = LoggerFactory.getLogger(SeguidorResource.class);

    private static final String ENTITY_NAME = "seguidor";

    private final SeguidorService seguidorService;

    public SeguidorResource(SeguidorService seguidorService) {
        this.seguidorService = seguidorService;
    }

    /**
     * POST  /seguidors : Create a new seguidor.
     *
     * @param seguidorDTO the seguidorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seguidorDTO, or with status 400 (Bad Request) if the seguidor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seguidors")
    @Timed
    public ResponseEntity<SeguidorDTO> createSeguidor(@RequestBody SeguidorDTO seguidorDTO) throws URISyntaxException {
        log.debug("REST request to save Seguidor : {}", seguidorDTO);
        if (seguidorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new seguidor cannot already have an ID")).body(null);
        }
        SeguidorDTO result = seguidorService.save(seguidorDTO);
        return ResponseEntity.created(new URI("/api/seguidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seguidors : Updates an existing seguidor.
     *
     * @param seguidorDTO the seguidorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seguidorDTO,
     * or with status 400 (Bad Request) if the seguidorDTO is not valid,
     * or with status 500 (Internal Server Error) if the seguidorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seguidors")
    @Timed
    public ResponseEntity<SeguidorDTO> updateSeguidor(@RequestBody SeguidorDTO seguidorDTO) throws URISyntaxException {
        log.debug("REST request to update Seguidor : {}", seguidorDTO);
        if (seguidorDTO.getId() == null) {
            return createSeguidor(seguidorDTO);
        }
        SeguidorDTO result = seguidorService.save(seguidorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seguidorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seguidors : get all the seguidors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seguidors in body
     */
    @GetMapping("/seguidors")
    @Timed
    public List<SeguidorDTO> getAllSeguidors() {
        log.debug("REST request to get all Seguidors");
        return seguidorService.findAll();
        }

    /**
     * GET  /seguidors/:id : get the "id" seguidor.
     *
     * @param id the id of the seguidorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seguidorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seguidors/{id}")
    @Timed
    public ResponseEntity<SeguidorDTO> getSeguidor(@PathVariable Long id) {
        log.debug("REST request to get Seguidor : {}", id);
        SeguidorDTO seguidorDTO = seguidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seguidorDTO));
    }

    /**
     * DELETE  /seguidors/:id : delete the "id" seguidor.
     *
     * @param id the id of the seguidorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seguidors/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeguidor(@PathVariable Long id) {
        log.debug("REST request to delete Seguidor : {}", id);
        seguidorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
