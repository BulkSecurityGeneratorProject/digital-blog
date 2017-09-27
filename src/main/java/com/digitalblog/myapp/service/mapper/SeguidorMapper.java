package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.SeguidorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Seguidor and its DTO SeguidorDTO.
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, })
public interface SeguidorMapper extends EntityMapper <SeguidorDTO, Seguidor> {

    @Mapping(source = "idSeguidor.id", target = "idSeguidorId")

    @Mapping(source = "idSeguido.id", target = "idSeguidoId")
    SeguidorDTO toDto(Seguidor seguidor); 

    @Mapping(source = "idSeguidorId", target = "idSeguidor")

    @Mapping(source = "idSeguidoId", target = "idSeguido")
    Seguidor toEntity(SeguidorDTO seguidorDTO); 
    default Seguidor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seguidor seguidor = new Seguidor();
        seguidor.setId(id);
        return seguidor;
    }
}
