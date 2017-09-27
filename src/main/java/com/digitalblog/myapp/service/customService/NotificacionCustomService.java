package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.NotificacionDTO;

import java.util.List;

/**
 * Created by Maureen on 3/23/2017.
 */
public interface NotificacionCustomService {

    List<NotificacionDTO> findAllByUser(Integer id);

    List<NotificacionDTO> notificacionPublicaciones(Integer id);

    List<NotificacionDTO> getNotificaciones(Integer id, Integer opc);

    NotificacionDTO cambiarEstado(Long id);

    Integer obtenerNotificcionesNoLeidas (Long id);

    List<NotificacionDTO>  cambiarNotiLikeEstado(Long id);
}
