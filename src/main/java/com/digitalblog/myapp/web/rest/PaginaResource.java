package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.PaginaService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.PaginaDTO;
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
 * REST controller for managing Pagina.
 */
@RestController
@RequestMapping("/api")
public class PaginaResource {

    private final Logger log = LoggerFactory.getLogger(PaginaResource.class);

    private static final String ENTITY_NAME = "pagina";
        
    private final PaginaService paginaService;

    public PaginaResource(PaginaService paginaService) {
        this.paginaService = paginaService;
    }

    /**
     * POST  /paginas : Create a new pagina.
     *
     * @param paginaDTO the paginaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paginaDTO, or with status 400 (Bad Request) if the pagina has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/paginas")
    @Timed
    public ResponseEntity<PaginaDTO> createPagina(@RequestBody PaginaDTO paginaDTO) throws URISyntaxException {
        log.debug("REST request to save Pagina : {}", paginaDTO);
        if (paginaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pagina cannot already have an ID")).body(null);
        }
        PaginaDTO result = paginaService.save(paginaDTO);
        return ResponseEntity.created(new URI("/api/paginas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /paginas : Updates an existing pagina.
     *
     * @param paginaDTO the paginaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paginaDTO,
     * or with status 400 (Bad Request) if the paginaDTO is not valid,
     * or with status 500 (Internal Server Error) if the paginaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/paginas")
    @Timed
    public ResponseEntity<PaginaDTO> updatePagina(@RequestBody PaginaDTO paginaDTO) throws URISyntaxException {
        log.debug("REST request to update Pagina : {}", paginaDTO);
        if (paginaDTO.getId() == null) {
            return createPagina(paginaDTO);
        }
        PaginaDTO result = paginaService.save(paginaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paginaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /paginas : get all the paginas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paginas in body
     */
    @GetMapping("/paginas")
    @Timed
    public List<PaginaDTO> getAllPaginas() {
        log.debug("REST request to get all Paginas");
        return paginaService.findAll();
    }

    /**
     * GET  /paginas/:id : get the "id" pagina.
     *
     * @param id the id of the paginaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paginaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/paginas/{id}")
    @Timed
    public ResponseEntity<PaginaDTO> getPagina(@PathVariable Long id) {
        log.debug("REST request to get Pagina : {}", id);
        PaginaDTO paginaDTO = paginaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paginaDTO));
    }

    /**
     * DELETE  /paginas/:id : delete the "id" pagina.
     *
     * @param id the id of the paginaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/paginas/{id}")
    @Timed
    public ResponseEntity<Void> deletePagina(@PathVariable Long id) {
        log.debug("REST request to delete Pagina : {}", id);
        paginaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
