package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.PaginaService;
import com.digitalblog.myapp.service.customService.PaginaCustomService;
import com.digitalblog.myapp.service.dto.PaginaDTO;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * REST controller for managing Pagina.
 */
@RestController
@RequestMapping("/apiCustom")
public class PaginaResourceCustom {

    private final Logger log = LoggerFactory.getLogger(PaginaResourceCustom.class);

    private static final String ENTITY_NAME = "pagina";

    private final PaginaService paginaService;

    private final PaginaCustomService paginaCustomService;

    public PaginaResourceCustom(PaginaService paginaService, PaginaCustomService paginaCustomService) {
        this.paginaService = paginaService;
        this.paginaCustomService = paginaCustomService;
    }

    /**
     * @author Jose Nuñez
     * Método que crea paginas con respecto a los capitulos
     * @param paginaDTO
     * @return PaginaDTO
     * @throws URISyntaxException
     * @version 1.0
     */
    @PostMapping("/crearPaginaIn")
    @Timed
    public ResponseEntity<PaginaDTO> createPagina(@RequestBody PaginaDTO paginaDTO) throws URISyntaxException {
        log.debug("REST request to save Pagina : {}", paginaDTO);
        if (paginaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pagina cannot already have an ID")).body(null);
        }
        //Antes de guardar tengo que buscar paginas por capitulo, con el PaginaCustomService
        ArrayList<PaginaDTO> paginasCapitulo = paginaCustomService.buscarPaginasPorCapitulo(paginaDTO.getCapituloId());
        if (paginasCapitulo.size() != 0){
            paginaDTO = paginaCustomService.obtenerUltimaPaginaCapitulo(paginasCapitulo,paginaDTO);
        }else{
            paginaDTO.setNumeroPagina(1);
        }
        PaginaDTO result = paginaService.save(paginaDTO);
        return ResponseEntity.created(new URI("/api/paginas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * @author Jose Nuñez
     * Método que hace update a las paginas
     * @param paginaDTO
     * @return PaginaDTO
     * @throws URISyntaxException
     * @version 1.0
     */
    @PutMapping("/crearPaginaIn")
    @Timed
    public ResponseEntity<PaginaDTO> updatePagina(@RequestBody PaginaDTO paginaDTO) throws URISyntaxException {
        log.debug("REST request to save Pagina : {}", paginaDTO);
        if (paginaDTO.getId() == null) {
            return createPagina(paginaDTO);
        }
        //Antes de guardar tengo que buscar paginas por capituli, con el PaginaCustomService
        if (paginaDTO.getId() == null){
            ArrayList<PaginaDTO> paginasCapitulo = paginaCustomService.buscarPaginasPorCapitulo(paginaDTO.getCapituloId());
            if (paginasCapitulo.size() != 0){
                paginaDTO = paginaCustomService.obtenerUltimaPaginaCapitulo(paginasCapitulo,paginaDTO);
            }else{
                paginaDTO.setNumeroPagina(1);
            }
        }
        PaginaDTO result = paginaService.save(paginaDTO);
        return ResponseEntity.created(new URI("/api/paginas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * @author Jose Nuñez
     * Obtiene la pagina anterior
     * @param id
     * @param idCapitulo
     * @return PaginaDTO
     * @throws URISyntaxException
     * @version 1.0
     */
    @GetMapping("/verPaginaAnterior/{id}&{idCapitulo}")
    @Timed
    public ResponseEntity<PaginaDTO> getPagina(@PathVariable Long id,@PathVariable Long idCapitulo) {
        log.debug("REST request to get Pagina : {}", id);
        PaginaDTO ultimaPaginaTemporal = paginaService.findOne(id);
        PaginaDTO paginaDTO = paginaCustomService.obtenerPaginaAnterior(ultimaPaginaTemporal.getNumeroPagina(),idCapitulo);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paginaDTO));
    }

    /**
     * @author Jose Nuñez
     * Método que obtiene las paginas por el id de un capitulo
     * @param id
     * @return PaginaDTO
     * @throws URISyntaxException
     * @version 1.0
     */
    @GetMapping("/paginasCapitulo/{id}")
    @Timed
    public ResponseEntity<ArrayList<PaginaDTO>> getPaginasCapitulo(@PathVariable Long id) {
        ArrayList<PaginaDTO> paginasDTO = paginaCustomService.buscarPaginasPorCapitulo(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paginasDTO));
    }


}
