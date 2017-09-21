package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;


import com.digitalblog.myapp.service.customService.NotaServiceCustom;
import com.digitalblog.myapp.service.dto.NotaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apiCustom")
public class NotaDePublicacionResourceCustom {
    private final Logger log = LoggerFactory.getLogger(NotaDePublicacionResourceCustom.class);

    private static final String ENTITY_NAME = "nota";

    private final NotaServiceCustom notaServiceCustom;

    public NotaDePublicacionResourceCustom(NotaServiceCustom notaServiceCustom) {
        this.notaServiceCustom = notaServiceCustom;
    }

    /**
     * @author Christopher Sullivan
     * Obtiene los comentarios sobre la publicacion
     * @param idPagina
     * @return List<NotaDTO>
     * @version 1.0
     */
    @GetMapping("/notaPagina/{idPagina}")
    @Timed
    public List<NotaDTO> getComentarioSobrePublicacion(@PathVariable Long idPagina) {
        List<NotaDTO>  notaDTO = notaServiceCustom.getNotaDePublicacion(idPagina);
        return notaDTO;
    }
}
