package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.RolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rol and its DTO RolDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RolMapper extends EntityMapper <RolDTO, Rol> {
    
    @Mapping(target = "idUsuarios", ignore = true)
    Rol toEntity(RolDTO rolDTO); 
    default Rol fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rol rol = new Rol();
        rol.setId(id);
        return rol;
    }
}
