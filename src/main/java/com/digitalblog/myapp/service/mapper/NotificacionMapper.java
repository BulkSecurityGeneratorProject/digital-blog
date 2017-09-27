package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.NotificacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Notificacion and its DTO NotificacionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NotificacionMapper extends EntityMapper <NotificacionDTO, Notificacion> {
    
    
    default Notificacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notificacion notificacion = new Notificacion();
        notificacion.setId(id);
        return notificacion;
    }
}
