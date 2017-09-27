package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.PublicacionReportadaService;
import com.digitalblog.myapp.service.customService.PublicacionReportadaCustomService;
import com.digitalblog.myapp.service.dto.PublicacionReportadaDTO;
import com.digitalblog.myapp.service.dto.UsuarioDTO;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
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
 * REST controller for managing PublicacionReportada.
 */
@RestController
@RequestMapping("/apiCustom")
public class PublicacionReportadaResourceCustom {

    private final Logger log = LoggerFactory.getLogger(PublicacionReportadaResourceCustom.class);

    private static final String ENTITY_NAME = "publicacionReportada";

    private final PublicacionReportadaCustomService publicacionReportadaCustomService;

    public PublicacionReportadaResourceCustom(PublicacionReportadaCustomService publicacionReportadaCustomService) {
        this.publicacionReportadaCustomService = publicacionReportadaCustomService;
    }

    /**
     * @author Eduardo Guerrero
     * GET  /publicacionesReportadasCustom/:id : get the "idPublicacion" publicacionReportada.
     * @param id el id de la publicacion a traer
     * @return the ResponseEntity with status 200 (OK) and with body the publicacionReportadaDTO, or with status 404 (Not Found)
     * @version 1.0
     */
    @GetMapping("/publicacionesReportadasCustom/{id}")
    @Timed
    public PublicacionReportadaDTO getPublicacionReportada(@PathVariable Integer id) {
        log.debug("REST request to get PublicacionReportada : {}", id);
        PublicacionReportadaDTO publicacionReportadaDTO = publicacionReportadaCustomService.findPublicacionByIdPublicacion(id);
        return publicacionReportadaDTO;
    }

}
