package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.NotificacionService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.NotificacionDTO;
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
 * REST controller for managing Notificacion.
 */
@RestController
@RequestMapping("/api")
public class NotificacionResource {

    private final Logger log = LoggerFactory.getLogger(NotificacionResource.class);

    private static final String ENTITY_NAME = "notificacion";

    private final NotificacionService notificacionService;

    public NotificacionResource(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    /**
     * POST  /notificacions : Create a new notificacion.
     *
     * @param notificacionDTO the notificacionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notificacionDTO, or with status 400 (Bad Request) if the notificacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notificacions")
    @Timed
    public ResponseEntity<NotificacionDTO> createNotificacion(@RequestBody NotificacionDTO notificacionDTO) throws URISyntaxException {
        log.debug("REST request to save Notificacion : {}", notificacionDTO);
        if (notificacionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new notificacion cannot already have an ID")).body(null);
        }
        NotificacionDTO result = notificacionService.save(notificacionDTO);
        return ResponseEntity.created(new URI("/api/notificacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notificacions : Updates an existing notificacion.
     *
     * @param notificacionDTO the notificacionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notificacionDTO,
     * or with status 400 (Bad Request) if the notificacionDTO is not valid,
     * or with status 500 (Internal Server Error) if the notificacionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notificacions")
    @Timed
    public ResponseEntity<NotificacionDTO> updateNotificacion(@RequestBody NotificacionDTO notificacionDTO) throws URISyntaxException {
        log.debug("REST request to update Notificacion : {}", notificacionDTO);
        if (notificacionDTO.getId() == null) {
            return createNotificacion(notificacionDTO);
        }
        NotificacionDTO result = notificacionService.save(notificacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notificacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notificacions : get all the notificacions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of notificacions in body
     */
    @GetMapping("/notificacions")
    @Timed
    public List<NotificacionDTO> getAllNotificacions() {
        log.debug("REST request to get all Notificacions");
        return notificacionService.findAll();
        }

    /**
     * GET  /notificacions/:id : get the "id" notificacion.
     *
     * @param id the id of the notificacionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notificacionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/notificacions/{id}")
    @Timed
    public ResponseEntity<NotificacionDTO> getNotificacion(@PathVariable Long id) {
        log.debug("REST request to get Notificacion : {}", id);
        NotificacionDTO notificacionDTO = notificacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notificacionDTO));
    }

    /**
     * DELETE  /notificacions/:id : delete the "id" notificacion.
     *
     * @param id the id of the notificacionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notificacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Long id) {
        log.debug("REST request to delete Notificacion : {}", id);
        notificacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
