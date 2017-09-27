package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.PublicacionReportadaService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.PublicacionReportadaDTO;
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
 * REST controller for managing PublicacionReportada.
 */
@RestController
@RequestMapping("/api")
public class PublicacionReportadaResource {

    private final Logger log = LoggerFactory.getLogger(PublicacionReportadaResource.class);

    private static final String ENTITY_NAME = "publicacionReportada";
        
    private final PublicacionReportadaService publicacionReportadaService;

    public PublicacionReportadaResource(PublicacionReportadaService publicacionReportadaService) {
        this.publicacionReportadaService = publicacionReportadaService;
    }

    /**
     * POST  /publicacion-reportadas : Create a new publicacionReportada.
     *
     * @param publicacionReportadaDTO the publicacionReportadaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new publicacionReportadaDTO, or with status 400 (Bad Request) if the publicacionReportada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publicacion-reportadas")
    @Timed
    public ResponseEntity<PublicacionReportadaDTO> createPublicacionReportada(@RequestBody PublicacionReportadaDTO publicacionReportadaDTO) throws URISyntaxException {
        log.debug("REST request to save PublicacionReportada : {}", publicacionReportadaDTO);
        if (publicacionReportadaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new publicacionReportada cannot already have an ID")).body(null);
        }
        PublicacionReportadaDTO result = publicacionReportadaService.save(publicacionReportadaDTO);
        return ResponseEntity.created(new URI("/api/publicacion-reportadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /publicacion-reportadas : Updates an existing publicacionReportada.
     *
     * @param publicacionReportadaDTO the publicacionReportadaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated publicacionReportadaDTO,
     * or with status 400 (Bad Request) if the publicacionReportadaDTO is not valid,
     * or with status 500 (Internal Server Error) if the publicacionReportadaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/publicacion-reportadas")
    @Timed
    public ResponseEntity<PublicacionReportadaDTO> updatePublicacionReportada(@RequestBody PublicacionReportadaDTO publicacionReportadaDTO) throws URISyntaxException {
        log.debug("REST request to update PublicacionReportada : {}", publicacionReportadaDTO);
        if (publicacionReportadaDTO.getId() == null) {
            return createPublicacionReportada(publicacionReportadaDTO);
        }
        PublicacionReportadaDTO result = publicacionReportadaService.save(publicacionReportadaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, publicacionReportadaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /publicacion-reportadas : get all the publicacionReportadas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of publicacionReportadas in body
     */
    @GetMapping("/publicacion-reportadas")
    @Timed
    public List<PublicacionReportadaDTO> getAllPublicacionReportadas() {
        log.debug("REST request to get all PublicacionReportadas");
        return publicacionReportadaService.findAll();
    }

    /**
     * GET  /publicacion-reportadas/:id : get the "id" publicacionReportada.
     *
     * @param id the id of the publicacionReportadaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the publicacionReportadaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/publicacion-reportadas/{id}")
    @Timed
    public ResponseEntity<PublicacionReportadaDTO> getPublicacionReportada(@PathVariable Long id) {
        log.debug("REST request to get PublicacionReportada : {}", id);
        PublicacionReportadaDTO publicacionReportadaDTO = publicacionReportadaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(publicacionReportadaDTO));
    }

    /**
     * DELETE  /publicacion-reportadas/:id : delete the "id" publicacionReportada.
     *
     * @param id the id of the publicacionReportadaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/publicacion-reportadas/{id}")
    @Timed
    public ResponseEntity<Void> deletePublicacionReportada(@PathVariable Long id) {
        log.debug("REST request to delete PublicacionReportada : {}", id);
        publicacionReportadaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
