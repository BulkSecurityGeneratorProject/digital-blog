package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.SeguidorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Seguidor and its DTO SeguidorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SeguidorMapper {

    @Mapping(source = "idSeguidor.id", target = "idSeguidorId")
    @Mapping(source = "idSeguido.id", target = "idSeguidoId")
    SeguidorDTO seguidorToSeguidorDTO(Seguidor seguidor);

    List<SeguidorDTO> seguidorsToSeguidorDTOs(List<Seguidor> seguidors);

    @Mapping(source = "idSeguidorId", target = "idSeguidor")
    @Mapping(source = "idSeguidoId", target = "idSeguido")
    Seguidor seguidorDTOToSeguidor(SeguidorDTO seguidorDTO);

    List<Seguidor> seguidorDTOsToSeguidors(List<SeguidorDTO> seguidorDTOs);

    default Usuario usuarioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
