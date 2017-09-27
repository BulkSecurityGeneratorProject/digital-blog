package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.SeccionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Seccion and its DTO SeccionDTO.
 */
@Mapper(componentModel = "spring", uses = {BibliotecaMapper.class, })
public interface SeccionMapper extends EntityMapper <SeccionDTO, Seccion> {

    @Mapping(source = "biblioteca.id", target = "bibliotecaId")
    SeccionDTO toDto(Seccion seccion); 

    @Mapping(source = "bibliotecaId", target = "biblioteca")
    Seccion toEntity(SeccionDTO seccionDTO); 
    default Seccion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seccion seccion = new Seccion();
        seccion.setId(id);
        return seccion;
    }
}
