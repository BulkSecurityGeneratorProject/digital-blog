package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.TemaService;
import com.digitalblog.myapp.service.customService.TemaCustomService;
import com.digitalblog.myapp.service.dto.TemaDTO;
import com.digitalblog.myapp.web.rest.TemaResource;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by jgm16 on 09/03/2017.
 */
@RestController
@RequestMapping("/apiCustom")
public class TemaResourceCustom {
    private final Logger log = LoggerFactory.getLogger(TemaResourceCustom.class);

    private static final String ENTITY_NAME = "tema";

    private final TemaCustomService temaCustomService;

    private final TemaService temaService;

    public TemaResourceCustom(TemaCustomService temaCustomService,TemaService temaService) {
        this.temaCustomService = temaCustomService;
        this.temaService = temaService;
    }


    /**
     * @author Eduardo Guerrero
     * Guarda el tema en la bd
     * @param temaDTO recibe el objeto de tipo tema
     * @return DTO response entity para saber si todo funciono bien
     * @throws URISyntaxException 200,400,500 errors
     * @version 1.0
     */
    @PostMapping("/temas")
    @Timed
    public ResponseEntity<TemaDTO> createTema(@RequestBody TemaDTO temaDTO) throws URISyntaxException {
        log.debug("REST request to save Tema : {}", temaDTO);
        if (temaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tema cannot already have an ID")).body(null);
        }
            TemaDTO result = temaCustomService.save(temaDTO);
            return ResponseEntity.created(new URI("/apiCustom/temas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }

}
