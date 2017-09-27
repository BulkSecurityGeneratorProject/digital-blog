package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.NotaService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.NotaDTO;
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
 * REST controller for managing Nota.
 */
@RestController
@RequestMapping("/api")
public class NotaResource {

    private final Logger log = LoggerFactory.getLogger(NotaResource.class);

    private static final String ENTITY_NAME = "nota";

    private final NotaService notaService;

    public NotaResource(NotaService notaService) {
        this.notaService = notaService;
    }

    /**
     * POST  /notas : Create a new nota.
     *
     * @param notaDTO the notaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notaDTO, or with status 400 (Bad Request) if the nota has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notas")
    @Timed
    public ResponseEntity<NotaDTO> createNota(@RequestBody NotaDTO notaDTO) throws URISyntaxException {
        log.debug("REST request to save Nota : {}", notaDTO);
        if (notaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new nota cannot already have an ID")).body(null);
        }
        NotaDTO result = notaService.save(notaDTO);
        return ResponseEntity.created(new URI("/api/notas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notas : Updates an existing nota.
     *
     * @param notaDTO the notaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notaDTO,
     * or with status 400 (Bad Request) if the notaDTO is not valid,
     * or with status 500 (Internal Server Error) if the notaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notas")
    @Timed
    public ResponseEntity<NotaDTO> updateNota(@RequestBody NotaDTO notaDTO) throws URISyntaxException {
        log.debug("REST request to update Nota : {}", notaDTO);
        if (notaDTO.getId() == null) {
            return createNota(notaDTO);
        }
        NotaDTO result = notaService.save(notaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notas : get all the notas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of notas in body
     */
    @GetMapping("/notas")
    @Timed
    public List<NotaDTO> getAllNotas() {
        log.debug("REST request to get all Notas");
        return notaService.findAll();
        }

    /**
     * GET  /notas/:id : get the "id" nota.
     *
     * @param id the id of the notaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/notas/{id}")
    @Timed
    public ResponseEntity<NotaDTO> getNota(@PathVariable Long id) {
        log.debug("REST request to get Nota : {}", id);
        NotaDTO notaDTO = notaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notaDTO));
    }

    /**
     * DELETE  /notas/:id : delete the "id" nota.
     *
     * @param id the id of the notaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notas/{id}")
    @Timed
    public ResponseEntity<Void> deleteNota(@PathVariable Long id) {
        log.debug("REST request to delete Nota : {}", id);
        notaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
