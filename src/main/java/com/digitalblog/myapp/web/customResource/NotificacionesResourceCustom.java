package com.digitalblog.myapp.web.customResource;


import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.customService.NotificacionCustomService;
import com.digitalblog.myapp.service.dto.NotificacionDTO;
import com.digitalblog.myapp.web.rest.NotificacionResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiCustom")
public class NotificacionesResourceCustom {


    private final Logger log = LoggerFactory.getLogger(NotificacionResource.class);

    private static final String ENTITY_NAME = "notificacion";

    private final NotificacionCustomService notificacionService;

    public NotificacionesResourceCustom(NotificacionCustomService notificacionService) {
        this.notificacionService = notificacionService;
    }

    /**
     * @author Maureen Leon
     * GET  /obtenerNotificaciones/{id} : Obtiene notificaciones que pertenecen a un usuario
     * @param id del usuario
     * @return List<NotificacionDTO>
     * @version 1.0
     */
    @GetMapping("/obtenerNotificaciones/{id}")
    @Timed
    public List<NotificacionDTO> getAllNotificacions(@PathVariable Integer id) {
        log.debug("REST request to get all Notificacions");
        return notificacionService.findAllByUser(id);
    }

    /**
     * @author Maureen Leon
     * GET  /notificacionPublicaciones/{id} : Obtiene notificaciones por cada nueva publicacion de un ususario seguido
     * @param id the Long del usuario
     * @return List<NotificacionDTO>
     * @version 1.0
     */
    @GetMapping("/notificacionPublicaciones/{id}")
    @Timed
    public List<NotificacionDTO> notificacionPublicaciones(@PathVariable Integer id) {
        log.debug("REST request to get all Notificacions");
        return notificacionService.notificacionPublicaciones(id);
    }

    /**
     * @author Maureen Leon
     * GET  /notificacion/{id}{opc}:
     * @param id the Long del usuario
     * @param opc the Long del usuario
     * @return List<NotificacionDTO>
     * @version 1.0
     */
    @GetMapping("/notificacion/{id}&{opc}")
    @Timed
    public List<NotificacionDTO> getNotificaciones(@PathVariable Integer id,@PathVariable Integer opc) {
        log.debug("REST request to get all Notificacions");
        return notificacionService.getNotificaciones(id,opc);
    }

    /**
     * @author Maureen Leon
     * GET  /cambiarEstadoNoticacion/{id}:
     * @param id the Long de la notificacion
     * @return NotificacionDTO
     * @version 1.0
     */
    @GetMapping("/cambiarEstadoNoticacion/{id}")
    @Timed
    public NotificacionDTO cambiarEstadoNoticacion(@PathVariable Long id) {
        log.debug("REST request to get all Notificacions");
        return notificacionService.cambiarEstado(id);

    }

    @GetMapping("/notificcionesNoLeidas/{id}")
    @Timed
    public Integer obtenerNotificcionesNoLeidas(@PathVariable Long id){
        return notificacionService.obtenerNotificcionesNoLeidas(id);
    }

     @PutMapping("/cambiarNotiLikeEstado/{id}")
    @Timed
    public List<NotificacionDTO>  cambiarNotiLikeEstado(@PathVariable Long id){
         notificacionService.cambiarNotiLikeEstado(id);
         return null;
    }

}
