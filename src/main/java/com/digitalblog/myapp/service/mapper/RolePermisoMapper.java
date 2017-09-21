package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.RolePermisoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity RolePermiso and its DTO RolePermisoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RolePermisoMapper {

    @Mapping(source = "idPermiso.id", target = "idPermisoId")
    @Mapping(source = "idRol.id", target = "idRolId")
    RolePermisoDTO rolePermisoToRolePermisoDTO(RolePermiso rolePermiso);

    List<RolePermisoDTO> rolePermisosToRolePermisoDTOs(List<RolePermiso> rolePermisos);

    @Mapping(source = "idPermisoId", target = "idPermiso")
    @Mapping(source = "idRolId", target = "idRol")
    RolePermiso rolePermisoDTOToRolePermiso(RolePermisoDTO rolePermisoDTO);

    List<RolePermiso> rolePermisoDTOsToRolePermisos(List<RolePermisoDTO> rolePermisoDTOs);

    default Permiso permisoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Permiso permiso = new Permiso();
        permiso.setId(id);
        return permiso;
    }

    default Rol rolFromId(Long id) {
        if (id == null) {
            return null;
        }
        Rol rol = new Rol();
        rol.setId(id);
        return rol;
    }
}
