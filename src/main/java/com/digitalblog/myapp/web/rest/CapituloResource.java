package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.CapituloService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.CapituloDTO;
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
 * REST controller for managing Capitulo.
 */
@RestController
@RequestMapping("/api")
public class CapituloResource {

    private final Logger log = LoggerFactory.getLogger(CapituloResource.class);

    private static final String ENTITY_NAME = "capitulo";
        
    private final CapituloService capituloService;

    public CapituloResource(CapituloService capituloService) {
        this.capituloService = capituloService;
    }

    /**
     * POST  /capitulos : Create a new capitulo.
     *
     * @param capituloDTO the capituloDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new capituloDTO, or with status 400 (Bad Request) if the capitulo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/capitulos")
    @Timed
    public ResponseEntity<CapituloDTO> createCapitulo(@RequestBody CapituloDTO capituloDTO) throws URISyntaxException {
        log.debug("REST request to save Capitulo : {}", capituloDTO);
        if (capituloDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new capitulo cannot already have an ID")).body(null);
        }
        CapituloDTO result = capituloService.save(capituloDTO);
        return ResponseEntity.created(new URI("/api/capitulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /capitulos : Updates an existing capitulo.
     *
     * @param capituloDTO the capituloDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated capituloDTO,
     * or with status 400 (Bad Request) if the capituloDTO is not valid,
     * or with status 500 (Internal Server Error) if the capituloDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/capitulos")
    @Timed
    public ResponseEntity<CapituloDTO> updateCapitulo(@RequestBody CapituloDTO capituloDTO) throws URISyntaxException {
        log.debug("REST request to update Capitulo : {}", capituloDTO);
        if (capituloDTO.getId() == null) {
            return createCapitulo(capituloDTO);
        }
        CapituloDTO result = capituloService.save(capituloDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, capituloDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /capitulos : get all the capitulos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of capitulos in body
     */
    @GetMapping("/capitulos")
    @Timed
    public List<CapituloDTO> getAllCapitulos() {
        log.debug("REST request to get all Capitulos");
        return capituloService.findAll();
    }

    /**
     * GET  /capitulos/:id : get the "id" capitulo.
     *
     * @param id the id of the capituloDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the capituloDTO, or with status 404 (Not Found)
     */
    @GetMapping("/capitulos/{id}")
    @Timed
    public ResponseEntity<CapituloDTO> getCapitulo(@PathVariable Long id) {
        log.debug("REST request to get Capitulo : {}", id);
        CapituloDTO capituloDTO = capituloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(capituloDTO));
    }

    /**
     * DELETE  /capitulos/:id : delete the "id" capitulo.
     *
     * @param id the id of the capituloDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/capitulos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCapitulo(@PathVariable Long id) {
        log.debug("REST request to delete Capitulo : {}", id);
        capituloService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
