package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.SuscripcionesService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.SuscripcionesDTO;
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
 * REST controller for managing Suscripciones.
 */
@RestController
@RequestMapping("/api")
public class SuscripcionesResource {

    private final Logger log = LoggerFactory.getLogger(SuscripcionesResource.class);

    private static final String ENTITY_NAME = "suscripciones";

    private final SuscripcionesService suscripcionesService;

    public SuscripcionesResource(SuscripcionesService suscripcionesService) {
        this.suscripcionesService = suscripcionesService;
    }

    /**
     * POST  /suscripciones : Create a new suscripciones.
     *
     * @param suscripcionesDTO the suscripcionesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new suscripcionesDTO, or with status 400 (Bad Request) if the suscripciones has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/suscripciones")
    @Timed
    public ResponseEntity<SuscripcionesDTO> createSuscripciones(@RequestBody SuscripcionesDTO suscripcionesDTO) throws URISyntaxException {
        log.debug("REST request to save Suscripciones : {}", suscripcionesDTO);
        if (suscripcionesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new suscripciones cannot already have an ID")).body(null);
        }
        SuscripcionesDTO result = suscripcionesService.save(suscripcionesDTO);
        return ResponseEntity.created(new URI("/api/suscripciones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /suscripciones : Updates an existing suscripciones.
     *
     * @param suscripcionesDTO the suscripcionesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated suscripcionesDTO,
     * or with status 400 (Bad Request) if the suscripcionesDTO is not valid,
     * or with status 500 (Internal Server Error) if the suscripcionesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/suscripciones")
    @Timed
    public ResponseEntity<SuscripcionesDTO> updateSuscripciones(@RequestBody SuscripcionesDTO suscripcionesDTO) throws URISyntaxException {
        log.debug("REST request to update Suscripciones : {}", suscripcionesDTO);
        if (suscripcionesDTO.getId() == null) {
            return createSuscripciones(suscripcionesDTO);
        }
        SuscripcionesDTO result = suscripcionesService.save(suscripcionesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, suscripcionesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /suscripciones : get all the suscripciones.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of suscripciones in body
     */
    @GetMapping("/suscripciones")
    @Timed
    public List<SuscripcionesDTO> getAllSuscripciones() {
        log.debug("REST request to get all Suscripciones");
        return suscripcionesService.findAll();
        }

    /**
     * GET  /suscripciones/:id : get the "id" suscripciones.
     *
     * @param id the id of the suscripcionesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the suscripcionesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/suscripciones/{id}")
    @Timed
    public ResponseEntity<SuscripcionesDTO> getSuscripciones(@PathVariable Long id) {
        log.debug("REST request to get Suscripciones : {}", id);
        SuscripcionesDTO suscripcionesDTO = suscripcionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(suscripcionesDTO));
    }

    /**
     * DELETE  /suscripciones/:id : delete the "id" suscripciones.
     *
     * @param id the id of the suscripcionesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/suscripciones/{id}")
    @Timed
    public ResponseEntity<Void> deleteSuscripciones(@PathVariable Long id) {
        log.debug("REST request to delete Suscripciones : {}", id);
        suscripcionesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
