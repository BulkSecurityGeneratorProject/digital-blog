package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.ComentarioService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.ComentarioDTO;
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
 * REST controller for managing Comentario.
 */
@RestController
@RequestMapping("/api")
public class ComentarioResource {

    private final Logger log = LoggerFactory.getLogger(ComentarioResource.class);

    private static final String ENTITY_NAME = "comentario";
        
    private final ComentarioService comentarioService;

    public ComentarioResource(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    /**
     * POST  /comentarios : Create a new comentario.
     *
     * @param comentarioDTO the comentarioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comentarioDTO, or with status 400 (Bad Request) if the comentario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comentarios")
    @Timed
    public ResponseEntity<ComentarioDTO> createComentario(@RequestBody ComentarioDTO comentarioDTO) throws URISyntaxException {
        log.debug("REST request to save Comentario : {}", comentarioDTO);
        if (comentarioDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new comentario cannot already have an ID")).body(null);
        }
        ComentarioDTO result = comentarioService.save(comentarioDTO);
        return ResponseEntity.created(new URI("/api/comentarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comentarios : Updates an existing comentario.
     *
     * @param comentarioDTO the comentarioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comentarioDTO,
     * or with status 400 (Bad Request) if the comentarioDTO is not valid,
     * or with status 500 (Internal Server Error) if the comentarioDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comentarios")
    @Timed
    public ResponseEntity<ComentarioDTO> updateComentario(@RequestBody ComentarioDTO comentarioDTO) throws URISyntaxException {
        log.debug("REST request to update Comentario : {}", comentarioDTO);
        if (comentarioDTO.getId() == null) {
            return createComentario(comentarioDTO);
        }
        ComentarioDTO result = comentarioService.save(comentarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comentarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comentarios : get all the comentarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comentarios in body
     */
    @GetMapping("/comentarios")
    @Timed
    public List<ComentarioDTO> getAllComentarios() {
        log.debug("REST request to get all Comentarios");
        return comentarioService.findAll();
    }

    /**
     * GET  /comentarios/:id : get the "id" comentario.
     *
     * @param id the id of the comentarioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comentarioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/comentarios/{id}")
    @Timed
    public ResponseEntity<ComentarioDTO> getComentario(@PathVariable Long id) {
        log.debug("REST request to get Comentario : {}", id);
        ComentarioDTO comentarioDTO = comentarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(comentarioDTO));
    }

    /**
     * DELETE  /comentarios/:id : delete the "id" comentario.
     *
     * @param id the id of the comentarioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comentarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        log.debug("REST request to delete Comentario : {}", id);
        comentarioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
