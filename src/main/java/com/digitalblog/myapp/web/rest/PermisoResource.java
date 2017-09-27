package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.PermisoService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.PermisoDTO;
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
 * REST controller for managing Permiso.
 */
@RestController
@RequestMapping("/api")
public class PermisoResource {

    private final Logger log = LoggerFactory.getLogger(PermisoResource.class);

    private static final String ENTITY_NAME = "permiso";

    private final PermisoService permisoService;

    public PermisoResource(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    /**
     * POST  /permisos : Create a new permiso.
     *
     * @param permisoDTO the permisoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new permisoDTO, or with status 400 (Bad Request) if the permiso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/permisos")
    @Timed
    public ResponseEntity<PermisoDTO> createPermiso(@RequestBody PermisoDTO permisoDTO) throws URISyntaxException {
        log.debug("REST request to save Permiso : {}", permisoDTO);
        if (permisoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new permiso cannot already have an ID")).body(null);
        }
        PermisoDTO result = permisoService.save(permisoDTO);
        return ResponseEntity.created(new URI("/api/permisos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /permisos : Updates an existing permiso.
     *
     * @param permisoDTO the permisoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated permisoDTO,
     * or with status 400 (Bad Request) if the permisoDTO is not valid,
     * or with status 500 (Internal Server Error) if the permisoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/permisos")
    @Timed
    public ResponseEntity<PermisoDTO> updatePermiso(@RequestBody PermisoDTO permisoDTO) throws URISyntaxException {
        log.debug("REST request to update Permiso : {}", permisoDTO);
        if (permisoDTO.getId() == null) {
            return createPermiso(permisoDTO);
        }
        PermisoDTO result = permisoService.save(permisoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, permisoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /permisos : get all the permisos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of permisos in body
     */
    @GetMapping("/permisos")
    @Timed
    public List<PermisoDTO> getAllPermisos() {
        log.debug("REST request to get all Permisos");
        return permisoService.findAll();
        }

    /**
     * GET  /permisos/:id : get the "id" permiso.
     *
     * @param id the id of the permisoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the permisoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/permisos/{id}")
    @Timed
    public ResponseEntity<PermisoDTO> getPermiso(@PathVariable Long id) {
        log.debug("REST request to get Permiso : {}", id);
        PermisoDTO permisoDTO = permisoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(permisoDTO));
    }

    /**
     * DELETE  /permisos/:id : delete the "id" permiso.
     *
     * @param id the id of the permisoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/permisos/{id}")
    @Timed
    public ResponseEntity<Void> deletePermiso(@PathVariable Long id) {
        log.debug("REST request to delete Permiso : {}", id);
        permisoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
