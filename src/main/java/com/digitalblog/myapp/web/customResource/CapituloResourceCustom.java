package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.CapituloService;
import com.digitalblog.myapp.service.customService.CapituloCustomService;
import com.digitalblog.myapp.service.dto.CapituloDTO;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Capitulo.
 */
@RestController
@RequestMapping("/apiCustom")
public class CapituloResourceCustom {

    private final Logger log = LoggerFactory.getLogger(CapituloResourceCustom.class);

    private static final String ENTITY_NAME = "capitulo";

    private final CapituloService capituloService;

    private final CapituloCustomService capituloCustomService;

    public CapituloResourceCustom(CapituloService capituloService,CapituloCustomService capituloCustomService) {
        this.capituloService = capituloService;
        this.capituloCustomService = capituloCustomService;
    }


    /**
     * @author José Nuñez
     * Método que crea capitulos
     * @param capituloDTO capituloDTO
     * @return capituloDTO
     * @throws URISyntaxException
     * @version 1.0
     * */
    @PostMapping("/crearCapituloIn")
    @Timed
    public ResponseEntity<CapituloDTO> createCapituloIn(@RequestBody CapituloDTO capituloDTO) throws URISyntaxException {
        log.debug("REST request to save Capitulo : {}", capituloDTO);
        if (capituloDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new capitulo cannot already have an ID")).body(null);
        }
        //Antes de guardar tengo que buscar capitulos por publicacion, con el CapituloCustomService
        ArrayList<CapituloDTO> capitulosPublicacion = capituloCustomService.buscarCapituloPorPublicacion(capituloDTO.getIdPublicacionCId());
        if (capitulosPublicacion.size() != 0){
            capituloDTO = capituloCustomService.obtenerUltimoCapituloPublicacion(capitulosPublicacion,capituloDTO);
        }else{
            capituloDTO.setNumeroCapitulo(1);
        }
        CapituloDTO result = capituloService.save(capituloDTO);
        return ResponseEntity.created(new URI("/api/capitulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * @author José Nuñez
     * Método que obtiene los capitulos por id publicacion
     * @param id de la publicacion.
     * @return ArrayList<CapituloDTO>
     * @version 1.0
     */
    @GetMapping("/capitulosPublicacion/{id}")
    @Timed
    public ResponseEntity<ArrayList<CapituloDTO>> getCapitulosPublicacion(@PathVariable Long id) {
        log.debug("REST request to get Capitulo : {}", id);
        ArrayList<CapituloDTO> capitulosDTO = capituloCustomService.buscarCapituloPorPublicacion(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(capitulosDTO));
    }

    /**
     * @author José Nuñez
     * Método que crea los capitulos de una publicacion compartida
     * @param capitulos lista de capitulosDTO
     * @return List<CapituloDTO>
     * @version 1.0
     */
    @PostMapping("/crearCapituloCompatida")
    @Timed
    public List<CapituloDTO> crearCapituloCompatida(@RequestBody List<CapituloDTO> capitulos) throws URISyntaxException {

        ArrayList<CapituloDTO> listaCapitulos= new ArrayList<CapituloDTO>();
        Integer numCapitulo = 1;
        for(int i=0;i<capitulos.size();i++){
                capitulos.get(i).setNumeroCapitulo(numCapitulo);
                CapituloDTO capituloDTO = capituloService.save(capitulos.get(i));
                numCapitulo++;
            listaCapitulos.add(capituloDTO);
        }
        return listaCapitulos;
    }



    /**
     * @author Jose Nuñes
     *  Método que obtiene el capitulo anterior de la publicación compartida
     * @param idPub id de la publicacion
     * @param numeroCap numero de capitulo
     * @return CapituloDTO
     * @throws URISyntaxException
     * @version 1.0
     */
    @GetMapping("/capituloAnterior/{idPub}&{numeroCap}")
    @Timed
    public CapituloDTO capituloAnterior(@PathVariable Long idPub,@PathVariable Long numeroCap) throws URISyntaxException {

        ArrayList<CapituloDTO> listaCapitulos= new ArrayList<CapituloDTO>();
        listaCapitulos = capituloCustomService.buscarCapituloPorPublicacion(idPub);
        CapituloDTO capAnterior = new CapituloDTO();
        for (int i=0; i < listaCapitulos.size(); i++){
            //Obtener el capitulo anterior y luego lo retornamos
            if((numeroCap - 1) == listaCapitulos.get(i).getNumeroCapitulo()){
                capAnterior = listaCapitulos.get(i);
                break;
            }
        }
        return capAnterior;
    }


}
