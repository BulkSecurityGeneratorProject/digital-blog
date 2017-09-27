package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.PermisoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Permiso and its DTO PermisoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PermisoMapper extends EntityMapper <PermisoDTO, Permiso> {
    
    
    default Permiso fromId(Long id) {
        if (id == null) {
            return null;
        }
        Permiso permiso = new Permiso();
        permiso.setId(id);
        return permiso;
    }
}
