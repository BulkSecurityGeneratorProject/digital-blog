package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.LikeTService;
import com.digitalblog.myapp.service.customService.LikeTCustomService;
import com.digitalblog.myapp.service.dto.LikeTDTO;
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
 * REST controller for managing LikeT.
 */
@RestController
@RequestMapping("/apiCustom")
public class LikeTResourceCustom {

    private final Logger log = LoggerFactory.getLogger(LikeTResourceCustom.class);

    private static final String ENTITY_NAME = "likeT";

    private final LikeTService likeTService;

    private final LikeTCustomService likeTCustomService;

    public LikeTResourceCustom(LikeTService likeTService, LikeTCustomService likeTCustomService) {
        this.likeTService = likeTService;
        this.likeTCustomService = likeTCustomService;
    }

    /**
     * @author Eduardo Guerrero
     * POST  /like-ts : Create a new likeT.
     * Crea el like en la tabla like_t con el id del usuario y el id de la publicacion
     * @param likeTDTO the likeTDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new likeTDTO, or with status 400 (Bad Request) if the likeT has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @version 1.0
     */
    @PostMapping("/likePublicacion")
    @Timed
    public ResponseEntity<LikeTDTO> createLikeT(@RequestBody LikeTDTO likeTDTO) throws URISyntaxException {
        log.debug("REST request to save LikeT : {}", likeTDTO);
        if (likeTDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new likeT cannot already have an ID")).body(null);
        }
        LikeTDTO result = likeTService.save(likeTDTO);
        return ResponseEntity.created(new URI("/api/like-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    /**
     * @author Eduardo Guerrero
     * GET  /likeDeUsuario/:idUsuario &  :idPublicacion : get the "id" likeT.
     * @param idUsuario
     * @param idPublicacion
     * @return the ResponseEntity with status 200 (OK) and with body the likeTDTO, or with status 404 (Not Found)
     * @version 1.0
     */
    @GetMapping("/likeDeUsuario/{idUsuario}&{idPublicacion}")
    @Timed
    public ResponseEntity<LikeTDTO> getLikeT(@PathVariable Long idUsuario,@PathVariable Long idPublicacion) {

        LikeTDTO likeTDTO = likeTCustomService.obtenerLikeDeUsuario(idUsuario,idPublicacion);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(likeTDTO));
    }

    /**
     * @author Eduardo Guerrero
     * Obtiene todos los likes del usuario
     * @param idUsuario
     * @return lista de likes List<LikeTDTO>
     * @version 1.0
     */
    @GetMapping("/likesDeUsuario/{idUsuario}")
    @Timed
    public List<LikeTDTO> getLikesTDeUsuario(@PathVariable Long idUsuario) {
        return likeTCustomService.obtenerLikesDeUsuario(idUsuario);
    }






}
