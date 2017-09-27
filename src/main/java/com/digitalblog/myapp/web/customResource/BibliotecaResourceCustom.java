package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.BibliotecaService;
import com.digitalblog.myapp.service.PublicacionService;
import com.digitalblog.myapp.service.customService.BibliotecaCustomService;
import com.digitalblog.myapp.service.dto.BibliotecaDTO;
import com.digitalblog.myapp.service.dto.PublicacionDTO;
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
 * REST controller for managing Biblioteca.
 */
@RestController
@RequestMapping("/apiCustom")
public class BibliotecaResourceCustom {

    private final Logger log = LoggerFactory.getLogger(BibliotecaResourceCustom.class);

    private static final String ENTITY_NAME = "biblioteca";

    private final BibliotecaCustomService bibliotecaCustomService;

    private final PublicacionService publicacionService;


    public BibliotecaResourceCustom(BibliotecaService bibliotecaService, BibliotecaCustomService bibliotecaCustomService, PublicacionService publicacionService) {

        this.bibliotecaCustomService = bibliotecaCustomService;
        this.publicacionService = publicacionService;
    }
    /**
     * @author Eduardo Guerrero
     * GET  /bibliotecas/:id : get the "id" biblioteca.
     * Obtiene la biblioteca del jhi-user logueado.
     * @param id the id of the bibliotecaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bibliotecaDTO, or with status 404 (Not Found)
     * @version 1.0
     */
    @GetMapping("/obtenerBibliotecaPorJhiUser/{id}")
    @Timed
    public ResponseEntity<BibliotecaDTO> getBiblioteca(@PathVariable Long id) {
        log.debug("REST request to get Biblioteca : {}", id);
        BibliotecaDTO bibliotecaDTO = bibliotecaCustomService.buscarBibliotecaPorJhiUser(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bibliotecaDTO));
    }

    /**
     * @author Eduardo Guerrero
     * PUT  /publicacions : Updates an existing publicacion.
     * Actualiza la publicacion.
     * @param publicacionDTO the publicacionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated publicacionDTO,
     * or with status 400 (Bad Request) if the publicacionDTO is not valid,
     * or with status 500 (Internal Server Error) if the publicacionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @version 1.0
     */
    @PutMapping("/updatePublicacion")
    @Timed
    public ResponseEntity<PublicacionDTO> updatePublicacion(@RequestBody PublicacionDTO publicacionDTO) throws URISyntaxException {
        log.debug("REST request to update Publicacion : {}", publicacionDTO);

        PublicacionDTO result = bibliotecaCustomService.savePublicacion(publicacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, publicacionDTO.getId().toString()))
            .body(result);
    }



}
