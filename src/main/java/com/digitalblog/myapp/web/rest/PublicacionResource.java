package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.PublicacionService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.PublicacionDTO;
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
 * REST controller for managing Publicacion.
 */
@RestController
@RequestMapping("/api")
public class PublicacionResource {

    private final Logger log = LoggerFactory.getLogger(PublicacionResource.class);

    private static final String ENTITY_NAME = "publicacion";

    private final PublicacionService publicacionService;

    public PublicacionResource(PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    /**
     * POST  /publicacions : Create a new publicacion.
     *
     * @param publicacionDTO the publicacionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new publicacionDTO, or with status 400 (Bad Request) if the publicacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publicacions")
    @Timed
    public ResponseEntity<PublicacionDTO> createPublicacion(@RequestBody PublicacionDTO publicacionDTO) throws URISyntaxException {
        log.debug("REST request to save Publicacion : {}", publicacionDTO);
        if (publicacionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new publicacion cannot already have an ID")).body(null);
        }
        PublicacionDTO result = publicacionService.save(publicacionDTO);
        return ResponseEntity.created(new URI("/api/publicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /publicacions : Updates an existing publicacion.
     *
     * @param publicacionDTO the publicacionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated publicacionDTO,
     * or with status 400 (Bad Request) if the publicacionDTO is not valid,
     * or with status 500 (Internal Server Error) if the publicacionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/publicacions")
    @Timed
    public ResponseEntity<PublicacionDTO> updatePublicacion(@RequestBody PublicacionDTO publicacionDTO) throws URISyntaxException {
        log.debug("REST request to update Publicacion : {}", publicacionDTO);
        if (publicacionDTO.getId() == null) {
            return createPublicacion(publicacionDTO);
        }
        PublicacionDTO result = publicacionService.save(publicacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, publicacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /publicacions : get all the publicacions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of publicacions in body
     */
    @GetMapping("/publicacions")
    @Timed
    public List<PublicacionDTO> getAllPublicacions() {
        log.debug("REST request to get all Publicacions");
        return publicacionService.findAll();
        }

    /**
     * GET  /publicacions/:id : get the "id" publicacion.
     *
     * @param id the id of the publicacionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the publicacionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/publicacions/{id}")
    @Timed
    public ResponseEntity<PublicacionDTO> getPublicacion(@PathVariable Long id) {
        log.debug("REST request to get Publicacion : {}", id);
        PublicacionDTO publicacionDTO = publicacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(publicacionDTO));
    }

    /**
     * DELETE  /publicacions/:id : delete the "id" publicacion.
     *
     * @param id the id of the publicacionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/publicacions/{id}")
    @Timed
    public ResponseEntity<Void> deletePublicacion(@PathVariable Long id) {
        log.debug("REST request to delete Publicacion : {}", id);
        publicacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
