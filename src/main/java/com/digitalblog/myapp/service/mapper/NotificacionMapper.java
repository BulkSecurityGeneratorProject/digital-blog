package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.NotificacionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Notificacion and its DTO NotificacionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NotificacionMapper {

    NotificacionDTO notificacionToNotificacionDTO(Notificacion notificacion);

    List<NotificacionDTO> notificacionsToNotificacionDTOs(List<Notificacion> notificacions);

    Notificacion notificacionDTOToNotificacion(NotificacionDTO notificacionDTO);

    List<Notificacion> notificacionDTOsToNotificacions(List<NotificacionDTO> notificacionDTOs);
}
