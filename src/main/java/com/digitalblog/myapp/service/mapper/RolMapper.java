package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.RolDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Rol and its DTO RolDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RolMapper {

    RolDTO rolToRolDTO(Rol rol);

    List<RolDTO> rolsToRolDTOs(List<Rol> rols);

    @Mapping(target = "idUsuarios", ignore = true)
    Rol rolDTOToRol(RolDTO rolDTO);

    List<Rol> rolDTOsToRols(List<RolDTO> rolDTOs);
}
