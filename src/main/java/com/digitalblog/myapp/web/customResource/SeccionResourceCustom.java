package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.domain.Seccion;
import com.digitalblog.myapp.service.SeccionService;
import com.digitalblog.myapp.service.customService.SeccionCustomService;
import com.digitalblog.myapp.service.dto.SeccionDTO;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Seccion.
 */
@RestController
@RequestMapping("/apiCustom")
public class SeccionResourceCustom {

    private final Logger log = LoggerFactory.getLogger(SeccionResourceCustom.class);

    private static final String ENTITY_NAME = "seccion";

    private final SeccionCustomService seccionCustomService;

    private final SeccionService seccionService;



    public SeccionResourceCustom(SeccionService seccionService, SeccionCustomService seccionCustomService, SeccionService seccionService1) {
        this.seccionCustomService = seccionCustomService;
        this.seccionService = seccionService1;
    }

    /**
     * POST  /seccions : Create a new seccion.
     * @author Eduardo Guerrero
     * @param seccionDTO the seccionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seccionDTO, or with status 400 (Bad Request) if the seccion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @version 1.0
     */
    @PostMapping("/agregarSecciones")
    @Timed
    public ResponseEntity<SeccionDTO> createSeccion(@RequestBody SeccionDTO seccionDTO) throws URISyntaxException {
        log.debug("REST request to save Seccion : {}", seccionDTO);
        SeccionDTO seccion = seccionCustomService.buscarSeccionPorNombre(seccionDTO.getNombre(),seccionDTO.getBibliotecaId());
        if (seccion != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "nameexists", "Una nueva seccion no puede tener un mismo nombre")).body(null);
        }
        SeccionDTO result = seccionService.save(seccionDTO);
        return ResponseEntity.created(new URI("/api/seccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seccions : Updates an existing seccion.
     * @author Eduardo Guerrero
     * @param seccionDTO the seccionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seccionDTO,
     * or with status 400 (Bad Request) if the seccionDTO is not valid,
     * or with status 500 (Internal Server Error) if the seccionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @version 1.0
     */
    @PutMapping("/actualizarSecciones")
    @Timed
    public ResponseEntity<SeccionDTO> updateSeccion(@RequestBody SeccionDTO seccionDTO) throws URISyntaxException {
        log.debug("REST request to update Seccion : {}", seccionDTO);
        if (seccionDTO.getId() == null) {
            return createSeccion(seccionDTO);
        }
        SeccionDTO result = seccionService.save(seccionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seccionDTO.getId().toString()))
            .body(result);
    }

    /**
     * @author Eduardo Guerrero
     * GET  /seccionesPorBiblioteca/{id} : Obtiene todas las secciones por una biblioteca
     * @param id the Long de la biblioteca
     * @return List<SeccionDTO>
     * @version 1.0
     */
    @GetMapping("/seccionesPorBiblioteca/{id}")
    @Timed
    public List<SeccionDTO> obtenerSeccionesPorBiblioteca(@PathVariable Long id) {
        log.debug("REST request to get all Seccions by Biblioteca");
        return seccionCustomService.buscarSeccionPorBiblioteca(id);
    }


}
