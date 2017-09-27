package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.NotaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Nota and its DTO NotaDTO.
 */
@Mapper(componentModel = "spring", uses = {PaginaMapper.class, })
public interface NotaMapper extends EntityMapper <NotaDTO, Nota> {

    @Mapping(source = "paginaNota.id", target = "paginaNotaId")
    NotaDTO toDto(Nota nota); 

    @Mapping(source = "paginaNotaId", target = "paginaNota")
    Nota toEntity(NotaDTO notaDTO); 
    default Nota fromId(Long id) {
        if (id == null) {
            return null;
        }
        Nota nota = new Nota();
        nota.setId(id);
        return nota;
    }
}
