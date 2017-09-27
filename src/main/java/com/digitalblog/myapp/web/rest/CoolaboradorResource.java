package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.CoolaboradorService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.CoolaboradorDTO;
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
 * REST controller for managing Coolaborador.
 */
@RestController
@RequestMapping("/api")
public class CoolaboradorResource {

    private final Logger log = LoggerFactory.getLogger(CoolaboradorResource.class);

    private static final String ENTITY_NAME = "coolaborador";

    private final CoolaboradorService coolaboradorService;

    public CoolaboradorResource(CoolaboradorService coolaboradorService) {
        this.coolaboradorService = coolaboradorService;
    }

    /**
     * POST  /coolaboradors : Create a new coolaborador.
     *
     * @param coolaboradorDTO the coolaboradorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coolaboradorDTO, or with status 400 (Bad Request) if the coolaborador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coolaboradors")
    @Timed
    public ResponseEntity<CoolaboradorDTO> createCoolaborador(@RequestBody CoolaboradorDTO coolaboradorDTO) throws URISyntaxException {
        log.debug("REST request to save Coolaborador : {}", coolaboradorDTO);
        if (coolaboradorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new coolaborador cannot already have an ID")).body(null);
        }
        CoolaboradorDTO result = coolaboradorService.save(coolaboradorDTO);
        return ResponseEntity.created(new URI("/api/coolaboradors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coolaboradors : Updates an existing coolaborador.
     *
     * @param coolaboradorDTO the coolaboradorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coolaboradorDTO,
     * or with status 400 (Bad Request) if the coolaboradorDTO is not valid,
     * or with status 500 (Internal Server Error) if the coolaboradorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coolaboradors")
    @Timed
    public ResponseEntity<CoolaboradorDTO> updateCoolaborador(@RequestBody CoolaboradorDTO coolaboradorDTO) throws URISyntaxException {
        log.debug("REST request to update Coolaborador : {}", coolaboradorDTO);
        if (coolaboradorDTO.getId() == null) {
            return createCoolaborador(coolaboradorDTO);
        }
        CoolaboradorDTO result = coolaboradorService.save(coolaboradorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coolaboradorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coolaboradors : get all the coolaboradors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coolaboradors in body
     */
    @GetMapping("/coolaboradors")
    @Timed
    public List<CoolaboradorDTO> getAllCoolaboradors() {
        log.debug("REST request to get all Coolaboradors");
        return coolaboradorService.findAll();
        }

    /**
     * GET  /coolaboradors/:id : get the "id" coolaborador.
     *
     * @param id the id of the coolaboradorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coolaboradorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/coolaboradors/{id}")
    @Timed
    public ResponseEntity<CoolaboradorDTO> getCoolaborador(@PathVariable Long id) {
        log.debug("REST request to get Coolaborador : {}", id);
        CoolaboradorDTO coolaboradorDTO = coolaboradorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coolaboradorDTO));
    }

    /**
     * DELETE  /coolaboradors/:id : delete the "id" coolaborador.
     *
     * @param id the id of the coolaboradorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coolaboradors/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoolaborador(@PathVariable Long id) {
        log.debug("REST request to delete Coolaborador : {}", id);
        coolaboradorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
