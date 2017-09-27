package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.SuscripcionesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Suscripciones and its DTO SuscripcionesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SuscripcionesMapper extends EntityMapper <SuscripcionesDTO, Suscripciones> {
    
    
    default Suscripciones fromId(Long id) {
        if (id == null) {
            return null;
        }
        Suscripciones suscripciones = new Suscripciones();
        suscripciones.setId(id);
        return suscripciones;
    }
}
