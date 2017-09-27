package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.TemaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tema and its DTO TemaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TemaMapper extends EntityMapper <TemaDTO, Tema> {
    
    
    default Tema fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tema tema = new Tema();
        tema.setId(id);
        return tema;
    }
}
