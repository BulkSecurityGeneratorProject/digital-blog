package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.PermisoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Permiso and its DTO PermisoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PermisoMapper {

    PermisoDTO permisoToPermisoDTO(Permiso permiso);

    List<PermisoDTO> permisosToPermisoDTOs(List<Permiso> permisos);

    Permiso permisoDTOToPermiso(PermisoDTO permisoDTO);

    List<Permiso> permisoDTOsToPermisos(List<PermisoDTO> permisoDTOs);
}
