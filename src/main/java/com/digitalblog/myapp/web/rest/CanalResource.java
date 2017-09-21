package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.CanalService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.CanalDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Canal.
 */
@RestController
@RequestMapping("/api")
public class CanalResource {

    private final Logger log = LoggerFactory.getLogger(CanalResource.class);

    private static final String ENTITY_NAME = "canal";
        
    private final CanalService canalService;

    public CanalResource(CanalService canalService) {
        this.canalService = canalService;
    }

    /**
     * POST  /canals : Create a new canal.
     *
     * @param canalDTO the canalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new canalDTO, or with status 400 (Bad Request) if the canal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/canals")
    @Timed
    public ResponseEntity<CanalDTO> createCanal(@RequestBody CanalDTO canalDTO) throws URISyntaxException {
        log.debug("REST request to save Canal : {}", canalDTO);
        if (canalDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new canal cannot already have an ID")).body(null);
        }
        CanalDTO result = canalService.save(canalDTO);
        return ResponseEntity.created(new URI("/api/canals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /canals : Updates an existing canal.
     *
     * @param canalDTO the canalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated canalDTO,
     * or with status 400 (Bad Request) if the canalDTO is not valid,
     * or with status 500 (Internal Server Error) if the canalDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/canals")
    @Timed
    public ResponseEntity<CanalDTO> updateCanal(@RequestBody CanalDTO canalDTO) throws URISyntaxException {
        log.debug("REST request to update Canal : {}", canalDTO);
        if (canalDTO.getId() == null) {
            return createCanal(canalDTO);
        }
        CanalDTO result = canalService.save(canalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, canalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /canals : get all the canals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of canals in body
     */
    @GetMapping("/canals")
    @Timed
    public List<CanalDTO> getAllCanals() {
        log.debug("REST request to get all Canals");
        return canalService.findAll();
    }

    /**
     * GET  /canals/:id : get the "id" canal.
     *
     * @param id the id of the canalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the canalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/canals/{id}")
    @Timed
    public ResponseEntity<CanalDTO> getCanal(@PathVariable Long id) {
        log.debug("REST request to get Canal : {}", id);
        CanalDTO canalDTO = canalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(canalDTO));
    }

    /**
     * DELETE  /canals/:id : delete the "id" canal.
     *
     * @param id the id of the canalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/canals/{id}")
    @Timed
    public ResponseEntity<Void> deleteCanal(@PathVariable Long id) {
        log.debug("REST request to delete Canal : {}", id);
        canalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
