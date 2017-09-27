package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.CapituloService;
import com.digitalblog.myapp.service.PublicacionService;
import com.digitalblog.myapp.service.customService.PublicacionCustomService;
import com.digitalblog.myapp.service.dto.CapituloDTO;
import com.digitalblog.myapp.service.dto.PublicacionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apiCustom")
public class PublicacionResourceCustom {

    private static final String ENTITY_NAME = "publicacion";
    private final PublicacionCustomService publicacionCustomService;
    private final PublicacionService publicacionService;
    private final CapituloService capituloService;

    public PublicacionResourceCustom(PublicacionCustomService publicacionCustomService, PublicacionService publicacionService, CapituloService capituloService) {
        this.publicacionCustomService = publicacionCustomService;
        this.publicacionService = publicacionService;
        this.capituloService = capituloService;
    }

    /**
     * @author Christopher Sullivan
     * GET  /getPublicaciones/{id} : Obtiene todas la publicaciones de un usuario
     * @param id the Long del usuario
     * @return List<PublicacionDTO>
     * @version 1.0
     */
    @GetMapping("/getPublicaciones/{id}")
    @Timed
    public List<PublicacionDTO> getPublicaciones(@PathVariable Long id) {
        return publicacionCustomService.findAllPublicaciones(id);
    }


    /**
     * @author Christopher Sullivan
     * se trae publicaciones por seccion especifica
     * @param seccion
     * @return List<PublicacionDTO>
     * @version 1.0
     */
    @GetMapping("/getPublicacionesTema/{seccion}")
    // @GetMapping("/getPublicacionesTema")
    @Timed
    //public List<PublicacionDTO> getPublicacionesTema(@PathVariable Integer idUsuarioLogged,@PathVariable Integer tema ) {
    public List<PublicacionDTO> getPublicacionesTema(@PathVariable Long seccion ) {
        return publicacionCustomService.findByTema(seccion);
    }



    /**
     * @author Maureen Leon
     * GET  /obtenerPublicacionesNotificacion :
     * Obtiene las publicaciones que generan una notificacion para mostrar su contenido
     * recibe un arreglo con los ids de las publicaciones
     * @param idPublicaciones
     * @return  List<PublicacionDTO>
     * @throws URISyntaxException
     * @version 1.0
     */
    @GetMapping("/obtenerPublicacionesNotificacion/{idPublicaciones}")
    @Timed
    public List<PublicacionDTO> obtenerPublicacionesNotificacion(@PathVariable List<Long> idPublicaciones) throws URISyntaxException {
        ArrayList<PublicacionDTO> listaPublicaciones = new ArrayList<>();
        for(int i=0;i<idPublicaciones.size();i++){
            PublicacionDTO publicacion = publicacionService.findOne(idPublicaciones.get(i));
            listaPublicaciones.add(publicacion);
        }
        return listaPublicaciones;
    }

    /**
     * @author Eduardo Guerrero
     * @version 1.0
     * Obtiene las publicaciones por autor,categoria,titulo ..etc
     * @param nombre de la seccion
     * @return  List<PublicacionDTO
     */
    @GetMapping("/publicacionesPorBusqueda/{nombre}")
    @Timed
    public List<PublicacionDTO> obtenerPublicacionesPorBusqueda(@PathVariable String nombre ) {
        String nombreParseado = "%" + nombre + "%";
        return publicacionCustomService.finByPublicacionesPorBusqueda(nombreParseado);
    }

    /**
     * @author Eduardo Guerrero
     * Obtiene todas las publicaciones por estado 2
     * @return List<PublicacionDTO>
     * @version 1.0
     */
    @GetMapping("/publicacionPublicada")
    @Timed
    public List<PublicacionDTO> obtenerPublicacionesPublicada() {
        return publicacionCustomService.finByPublicacionesPublicadas();
    }

    /**
     * @author Eduardo Guerrero
     * Obtiene todas las publicaciones por estad categoria y tema
     * @return List<PublicacionDTO>
     * @version 1.0
     */
    @GetMapping("/publicacionPorCategoriaYTema/{categoriaNombre}&{temaNombre}")
    @Timed
    public List<PublicacionDTO> publicacionPorCategoriaYTema(@PathVariable String categoriaNombre,@PathVariable String temaNombre) {
        return publicacionCustomService.findByCategoriaYTema(categoriaNombre,temaNombre);
    }




    /**
     * @author Maureen Leon
     * GET  /obtenerPublicacionesIdCapitulo :
     * @param capitulos
     * @return  List<PublicacionDTO>
     * @throws URISyntaxException
     * @version 1.0
     */
    @GetMapping("/obtenerPublicacionesIdCapitulo/{capitulos}")
    @Timed
    public List<PublicacionDTO> obtenerPublicacionesIdCapitulo(@PathVariable List<Long> capitulos) throws URISyntaxException {

        ArrayList<PublicacionDTO> listaPublicaciones = new ArrayList<>();

        for(int i=0;i<capitulos.size();i++){
            CapituloDTO capituloDTO = capituloService.findOne(capitulos.get(i));
            PublicacionDTO publicacion = publicacionService.findOne(capituloDTO.getIdPublicacionCId());
            listaPublicaciones.add(publicacion);
        }
        return listaPublicaciones;
    }
}
