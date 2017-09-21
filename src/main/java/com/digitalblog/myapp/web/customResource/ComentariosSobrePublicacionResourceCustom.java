package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.customService.ComentarioSobrePublicacionCustomService;
import com.digitalblog.myapp.service.dto.ComentarioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apiCustom")

public class ComentariosSobrePublicacionResourceCustom {
    private final Logger log = LoggerFactory.getLogger(ComentariosSobrePublicacionResourceCustom.class);

    private static final String ENTITY_NAME = "comentario";

    private final ComentarioSobrePublicacionCustomService comentarioCustomService;
    public ComentariosSobrePublicacionResourceCustom(ComentarioSobrePublicacionCustomService comentarioService) {
        this.comentarioCustomService = comentarioService;
    }

    /**
     * @author Christopher Sullivan
     * Obtiene los comentarios de la publicacion
     * @param idPublicacion
     * @return  List<ComentarioDTO>
     * @version 1.0
     */
    @GetMapping("/comentarioSobrePublicacion/{idPublicacion}")
    @Timed
    public List<ComentarioDTO> getComentarioSobrePublicacion(@PathVariable Long idPublicacion) {
        List<ComentarioDTO> comentarioDTO = comentarioCustomService.getComentarioSobrePublicacion(idPublicacion);
        return comentarioDTO;
    }
}
