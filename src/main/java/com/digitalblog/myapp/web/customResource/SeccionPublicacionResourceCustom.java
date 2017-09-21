package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.SeccionPublicacionService;
import com.digitalblog.myapp.service.customService.SeccionPublicacionCustomService;
import com.digitalblog.myapp.service.dto.SeccionPublicacionDTO;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing SeccionPublicacion.
 */
@RestController
@RequestMapping("/apiCustom")
public class SeccionPublicacionResourceCustom {

    private final Logger log = LoggerFactory.getLogger(SeccionPublicacionResourceCustom.class);

    private static final String ENTITY_NAME = "seccionPublicacion";

    private final SeccionPublicacionService seccionPublicacionService;

    private final SeccionPublicacionCustomService seccionPublicacionCustomService;





    public SeccionPublicacionResourceCustom(SeccionPublicacionService seccionPublicacionService, SeccionPublicacionCustomService seccionPublicacionCustomService) {
        this.seccionPublicacionService = seccionPublicacionService;
        this.seccionPublicacionCustomService = seccionPublicacionCustomService;
    }

    /**
     * @author Eduardo Guerrero
     * POST  /seccion-publicacions : Crea una nueva seccionPublicacion.
     * @param seccionPublicacionDTO la seccionPublicacionDTO a crear
     * @return the ResponseEntity with status 201 (Created) and with body the new seccionPublicacionDTO, or with status 400 (Bad Request) if the seccionPublicacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @version 1.0
     */
    @PostMapping("/seccion-publicacion")
    @Timed
    public ResponseEntity<SeccionPublicacionDTO> createSeccionPublicacion(@RequestBody SeccionPublicacionDTO seccionPublicacionDTO) throws URISyntaxException {
        log.debug("REST request to save SeccionPublicacion : {}", seccionPublicacionDTO);
        SeccionPublicacionDTO seccionPublicacion = seccionPublicacionCustomService.buscarPublicacionPorSeccion(seccionPublicacionDTO.getIdSeccionSPId(),seccionPublicacionDTO.getIdPublicacionSPId());

        if (seccionPublicacion != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "publicacionExist", "La publicacion ya existe en esa seccion")).body(null);
        }
        SeccionPublicacionDTO result = seccionPublicacionService.save(seccionPublicacionDTO);
        return ResponseEntity.created(new URI("/api/seccion-publicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * @author Maureen Leon
     * Obtiene la publicacion por seccion
     * @return el objeto buscado SeccionPublicacionDTO
     * @version 1.0
     */
    @PostMapping("/publicacions-seccion")
    @Timed
    public ResponseEntity<SeccionPublicacionDTO> obtenerPublicacionSeccion(@RequestBody SeccionPublicacionDTO seccionPublicacion) {
        seccionPublicacion = seccionPublicacionCustomService.findByIdPS(seccionPublicacion);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seccionPublicacion));
    }

}
