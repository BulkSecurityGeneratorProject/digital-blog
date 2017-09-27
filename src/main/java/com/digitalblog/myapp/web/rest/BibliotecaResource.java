package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.BibliotecaService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.BibliotecaDTO;
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
 * REST controller for managing Biblioteca.
 */
@RestController
@RequestMapping("/api")
public class BibliotecaResource {

    private final Logger log = LoggerFactory.getLogger(BibliotecaResource.class);

    private static final String ENTITY_NAME = "biblioteca";
        
    private final BibliotecaService bibliotecaService;

    public BibliotecaResource(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    /**
     * POST  /bibliotecas : Create a new biblioteca.
     *
     * @param bibliotecaDTO the bibliotecaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bibliotecaDTO, or with status 400 (Bad Request) if the biblioteca has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bibliotecas")
    @Timed
    public ResponseEntity<BibliotecaDTO> createBiblioteca(@RequestBody BibliotecaDTO bibliotecaDTO) throws URISyntaxException {
        log.debug("REST request to save Biblioteca : {}", bibliotecaDTO);
        if (bibliotecaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new biblioteca cannot already have an ID")).body(null);
        }
        BibliotecaDTO result = bibliotecaService.save(bibliotecaDTO);
        return ResponseEntity.created(new URI("/api/bibliotecas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bibliotecas : Updates an existing biblioteca.
     *
     * @param bibliotecaDTO the bibliotecaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bibliotecaDTO,
     * or with status 400 (Bad Request) if the bibliotecaDTO is not valid,
     * or with status 500 (Internal Server Error) if the bibliotecaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bibliotecas")
    @Timed
    public ResponseEntity<BibliotecaDTO> updateBiblioteca(@RequestBody BibliotecaDTO bibliotecaDTO) throws URISyntaxException {
        log.debug("REST request to update Biblioteca : {}", bibliotecaDTO);
        if (bibliotecaDTO.getId() == null) {
            return createBiblioteca(bibliotecaDTO);
        }
        BibliotecaDTO result = bibliotecaService.save(bibliotecaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bibliotecaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bibliotecas : get all the bibliotecas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bibliotecas in body
     */
    @GetMapping("/bibliotecas")
    @Timed
    public List<BibliotecaDTO> getAllBibliotecas() {
        log.debug("REST request to get all Bibliotecas");
        return bibliotecaService.findAll();
    }

    /**
     * GET  /bibliotecas/:id : get the "id" biblioteca.
     *
     * @param id the id of the bibliotecaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bibliotecaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bibliotecas/{id}")
    @Timed
    public ResponseEntity<BibliotecaDTO> getBiblioteca(@PathVariable Long id) {
        log.debug("REST request to get Biblioteca : {}", id);
        BibliotecaDTO bibliotecaDTO = bibliotecaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bibliotecaDTO));
    }

    /**
     * DELETE  /bibliotecas/:id : delete the "id" biblioteca.
     *
     * @param id the id of the bibliotecaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bibliotecas/{id}")
    @Timed
    public ResponseEntity<Void> deleteBiblioteca(@PathVariable Long id) {
        log.debug("REST request to delete Biblioteca : {}", id);
        bibliotecaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
