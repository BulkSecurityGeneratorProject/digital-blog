package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.RolePermisoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RolePermiso and its DTO RolePermisoDTO.
 */
@Mapper(componentModel = "spring", uses = {PermisoMapper.class, RolMapper.class, })
public interface RolePermisoMapper extends EntityMapper <RolePermisoDTO, RolePermiso> {

    @Mapping(source = "idPermiso.id", target = "idPermisoId")

    @Mapping(source = "idRol.id", target = "idRolId")
    RolePermisoDTO toDto(RolePermiso rolePermiso); 

    @Mapping(source = "idPermisoId", target = "idPermiso")

    @Mapping(source = "idRolId", target = "idRol")
    RolePermiso toEntity(RolePermisoDTO rolePermisoDTO); 
    default RolePermiso fromId(Long id) {
        if (id == null) {
            return null;
        }
        RolePermiso rolePermiso = new RolePermiso();
        rolePermiso.setId(id);
        return rolePermiso;
    }
}
