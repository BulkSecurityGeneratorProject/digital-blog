package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.ImagenPorPublicacionService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.ImagenPorPublicacionDTO;
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
 * REST controller for managing ImagenPorPublicacion.
 */
@RestController
@RequestMapping("/api")
public class ImagenPorPublicacionResource {

    private final Logger log = LoggerFactory.getLogger(ImagenPorPublicacionResource.class);

    private static final String ENTITY_NAME = "imagenPorPublicacion";

    private final ImagenPorPublicacionService imagenPorPublicacionService;

    public ImagenPorPublicacionResource(ImagenPorPublicacionService imagenPorPublicacionService) {
        this.imagenPorPublicacionService = imagenPorPublicacionService;
    }

    /**
     * POST  /imagen-por-publicacions : Create a new imagenPorPublicacion.
     *
     * @param imagenPorPublicacionDTO the imagenPorPublicacionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imagenPorPublicacionDTO, or with status 400 (Bad Request) if the imagenPorPublicacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/imagen-por-publicacions")
    @Timed
    public ResponseEntity<ImagenPorPublicacionDTO> createImagenPorPublicacion(@RequestBody ImagenPorPublicacionDTO imagenPorPublicacionDTO) throws URISyntaxException {
        log.debug("REST request to save ImagenPorPublicacion : {}", imagenPorPublicacionDTO);
        if (imagenPorPublicacionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new imagenPorPublicacion cannot already have an ID")).body(null);
        }
        ImagenPorPublicacionDTO result = imagenPorPublicacionService.save(imagenPorPublicacionDTO);
        return ResponseEntity.created(new URI("/api/imagen-por-publicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /imagen-por-publicacions : Updates an existing imagenPorPublicacion.
     *
     * @param imagenPorPublicacionDTO the imagenPorPublicacionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imagenPorPublicacionDTO,
     * or with status 400 (Bad Request) if the imagenPorPublicacionDTO is not valid,
     * or with status 500 (Internal Server Error) if the imagenPorPublicacionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/imagen-por-publicacions")
    @Timed
    public ResponseEntity<ImagenPorPublicacionDTO> updateImagenPorPublicacion(@RequestBody ImagenPorPublicacionDTO imagenPorPublicacionDTO) throws URISyntaxException {
        log.debug("REST request to update ImagenPorPublicacion : {}", imagenPorPublicacionDTO);
        if (imagenPorPublicacionDTO.getId() == null) {
            return createImagenPorPublicacion(imagenPorPublicacionDTO);
        }
        ImagenPorPublicacionDTO result = imagenPorPublicacionService.save(imagenPorPublicacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imagenPorPublicacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /imagen-por-publicacions : get all the imagenPorPublicacions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of imagenPorPublicacions in body
     */
    @GetMapping("/imagen-por-publicacions")
    @Timed
    public List<ImagenPorPublicacionDTO> getAllImagenPorPublicacions() {
        log.debug("REST request to get all ImagenPorPublicacions");
        return imagenPorPublicacionService.findAll();
        }

    /**
     * GET  /imagen-por-publicacions/:id : get the "id" imagenPorPublicacion.
     *
     * @param id the id of the imagenPorPublicacionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imagenPorPublicacionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/imagen-por-publicacions/{id}")
    @Timed
    public ResponseEntity<ImagenPorPublicacionDTO> getImagenPorPublicacion(@PathVariable Long id) {
        log.debug("REST request to get ImagenPorPublicacion : {}", id);
        ImagenPorPublicacionDTO imagenPorPublicacionDTO = imagenPorPublicacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(imagenPorPublicacionDTO));
    }

    /**
     * DELETE  /imagen-por-publicacions/:id : delete the "id" imagenPorPublicacion.
     *
     * @param id the id of the imagenPorPublicacionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/imagen-por-publicacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteImagenPorPublicacion(@PathVariable Long id) {
        log.debug("REST request to delete ImagenPorPublicacion : {}", id);
        imagenPorPublicacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
