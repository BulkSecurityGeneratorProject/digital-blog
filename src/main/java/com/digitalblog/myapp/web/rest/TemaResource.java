package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.TemaService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.TemaDTO;
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
 * REST controller for managing Tema.
 */
@RestController
@RequestMapping("/api")
public class TemaResource {

    private final Logger log = LoggerFactory.getLogger(TemaResource.class);

    private static final String ENTITY_NAME = "tema";

    private final TemaService temaService;

    public TemaResource(TemaService temaService) {
        this.temaService = temaService;
    }

    /**
     * POST  /temas : Create a new tema.
     *
     * @param temaDTO the temaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new temaDTO, or with status 400 (Bad Request) if the tema has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/temas")
    @Timed
    public ResponseEntity<TemaDTO> createTema(@RequestBody TemaDTO temaDTO) throws URISyntaxException {
        log.debug("REST request to save Tema : {}", temaDTO);
        if (temaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tema cannot already have an ID")).body(null);
        }
        TemaDTO result = temaService.save(temaDTO);
        return ResponseEntity.created(new URI("/api/temas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /temas : Updates an existing tema.
     *
     * @param temaDTO the temaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated temaDTO,
     * or with status 400 (Bad Request) if the temaDTO is not valid,
     * or with status 500 (Internal Server Error) if the temaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/temas")
    @Timed
    public ResponseEntity<TemaDTO> updateTema(@RequestBody TemaDTO temaDTO) throws URISyntaxException {
        log.debug("REST request to update Tema : {}", temaDTO);
        if (temaDTO.getId() == null) {
            return createTema(temaDTO);
        }
        TemaDTO result = temaService.save(temaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, temaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /temas : get all the temas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of temas in body
     */
    @GetMapping("/temas")
    @Timed
    public List<TemaDTO> getAllTemas() {
        log.debug("REST request to get all Temas");
        return temaService.findAll();
        }

    /**
     * GET  /temas/:id : get the "id" tema.
     *
     * @param id the id of the temaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the temaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/temas/{id}")
    @Timed
    public ResponseEntity<TemaDTO> getTema(@PathVariable Long id) {
        log.debug("REST request to get Tema : {}", id);
        TemaDTO temaDTO = temaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(temaDTO));
    }

    /**
     * DELETE  /temas/:id : delete the "id" tema.
     *
     * @param id the id of the temaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/temas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTema(@PathVariable Long id) {
        log.debug("REST request to delete Tema : {}", id);
        temaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
