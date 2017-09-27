package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.CapituloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Capitulo and its DTO CapituloDTO.
 */
@Mapper(componentModel = "spring", uses = {PublicacionMapper.class, })
public interface CapituloMapper extends EntityMapper <CapituloDTO, Capitulo> {

    @Mapping(source = "capitulo.id", target = "capituloId")

    @Mapping(source = "idPublicacionC.id", target = "idPublicacionCId")
    CapituloDTO toDto(Capitulo capitulo); 

    @Mapping(source = "capituloId", target = "capitulo")

    @Mapping(source = "idPublicacionCId", target = "idPublicacionC")
    Capitulo toEntity(CapituloDTO capituloDTO); 
    default Capitulo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Capitulo capitulo = new Capitulo();
        capitulo.setId(id);
        return capitulo;
    }
}
