package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.SeccionPublicacionService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.SeccionPublicacionDTO;
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
 * REST controller for managing SeccionPublicacion.
 */
@RestController
@RequestMapping("/api")
public class SeccionPublicacionResource {

    private final Logger log = LoggerFactory.getLogger(SeccionPublicacionResource.class);

    private static final String ENTITY_NAME = "seccionPublicacion";
        
    private final SeccionPublicacionService seccionPublicacionService;

    public SeccionPublicacionResource(SeccionPublicacionService seccionPublicacionService) {
        this.seccionPublicacionService = seccionPublicacionService;
    }

    /**
     * POST  /seccion-publicacions : Create a new seccionPublicacion.
     *
     * @param seccionPublicacionDTO the seccionPublicacionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seccionPublicacionDTO, or with status 400 (Bad Request) if the seccionPublicacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seccion-publicacions")
    @Timed
    public ResponseEntity<SeccionPublicacionDTO> createSeccionPublicacion(@RequestBody SeccionPublicacionDTO seccionPublicacionDTO) throws URISyntaxException {
        log.debug("REST request to save SeccionPublicacion : {}", seccionPublicacionDTO);
        if (seccionPublicacionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new seccionPublicacion cannot already have an ID")).body(null);
        }
        SeccionPublicacionDTO result = seccionPublicacionService.save(seccionPublicacionDTO);
        return ResponseEntity.created(new URI("/api/seccion-publicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seccion-publicacions : Updates an existing seccionPublicacion.
     *
     * @param seccionPublicacionDTO the seccionPublicacionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seccionPublicacionDTO,
     * or with status 400 (Bad Request) if the seccionPublicacionDTO is not valid,
     * or with status 500 (Internal Server Error) if the seccionPublicacionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seccion-publicacions")
    @Timed
    public ResponseEntity<SeccionPublicacionDTO> updateSeccionPublicacion(@RequestBody SeccionPublicacionDTO seccionPublicacionDTO) throws URISyntaxException {
        log.debug("REST request to update SeccionPublicacion : {}", seccionPublicacionDTO);
        if (seccionPublicacionDTO.getId() == null) {
            return createSeccionPublicacion(seccionPublicacionDTO);
        }
        SeccionPublicacionDTO result = seccionPublicacionService.save(seccionPublicacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seccionPublicacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seccion-publicacions : get all the seccionPublicacions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seccionPublicacions in body
     */
    @GetMapping("/seccion-publicacions")
    @Timed
    public List<SeccionPublicacionDTO> getAllSeccionPublicacions() {
        log.debug("REST request to get all SeccionPublicacions");
        return seccionPublicacionService.findAll();
    }

    /**
     * GET  /seccion-publicacions/:id : get the "id" seccionPublicacion.
     *
     * @param id the id of the seccionPublicacionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seccionPublicacionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seccion-publicacions/{id}")
    @Timed
    public ResponseEntity<SeccionPublicacionDTO> getSeccionPublicacion(@PathVariable Long id) {
        log.debug("REST request to get SeccionPublicacion : {}", id);
        SeccionPublicacionDTO seccionPublicacionDTO = seccionPublicacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seccionPublicacionDTO));
    }

    /**
     * DELETE  /seccion-publicacions/:id : delete the "id" seccionPublicacion.
     *
     * @param id the id of the seccionPublicacionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seccion-publicacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeccionPublicacion(@PathVariable Long id) {
        log.debug("REST request to delete SeccionPublicacion : {}", id);
        seccionPublicacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
