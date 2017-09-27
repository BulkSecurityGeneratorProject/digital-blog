package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.SeccionService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.SeccionDTO;
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
 * REST controller for managing Seccion.
 */
@RestController
@RequestMapping("/api")
public class SeccionResource {

    private final Logger log = LoggerFactory.getLogger(SeccionResource.class);

    private static final String ENTITY_NAME = "seccion";

    private final SeccionService seccionService;

    public SeccionResource(SeccionService seccionService) {
        this.seccionService = seccionService;
    }

    /**
     * POST  /seccions : Create a new seccion.
     *
     * @param seccionDTO the seccionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seccionDTO, or with status 400 (Bad Request) if the seccion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seccions")
    @Timed
    public ResponseEntity<SeccionDTO> createSeccion(@RequestBody SeccionDTO seccionDTO) throws URISyntaxException {
        log.debug("REST request to save Seccion : {}", seccionDTO);
        if (seccionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new seccion cannot already have an ID")).body(null);
        }
        SeccionDTO result = seccionService.save(seccionDTO);
        return ResponseEntity.created(new URI("/api/seccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seccions : Updates an existing seccion.
     *
     * @param seccionDTO the seccionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seccionDTO,
     * or with status 400 (Bad Request) if the seccionDTO is not valid,
     * or with status 500 (Internal Server Error) if the seccionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seccions")
    @Timed
    public ResponseEntity<SeccionDTO> updateSeccion(@RequestBody SeccionDTO seccionDTO) throws URISyntaxException {
        log.debug("REST request to update Seccion : {}", seccionDTO);
        if (seccionDTO.getId() == null) {
            return createSeccion(seccionDTO);
        }
        SeccionDTO result = seccionService.save(seccionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seccionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seccions : get all the seccions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seccions in body
     */
    @GetMapping("/seccions")
    @Timed
    public List<SeccionDTO> getAllSeccions() {
        log.debug("REST request to get all Seccions");
        return seccionService.findAll();
        }

    /**
     * GET  /seccions/:id : get the "id" seccion.
     *
     * @param id the id of the seccionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seccionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seccions/{id}")
    @Timed
    public ResponseEntity<SeccionDTO> getSeccion(@PathVariable Long id) {
        log.debug("REST request to get Seccion : {}", id);
        SeccionDTO seccionDTO = seccionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seccionDTO));
    }

    /**
     * DELETE  /seccions/:id : delete the "id" seccion.
     *
     * @param id the id of the seccionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seccions/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeccion(@PathVariable Long id) {
        log.debug("REST request to delete Seccion : {}", id);
        seccionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
