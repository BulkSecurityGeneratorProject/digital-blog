package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.PaginaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pagina and its DTO PaginaDTO.
 */
@Mapper(componentModel = "spring", uses = {CapituloMapper.class, })
public interface PaginaMapper extends EntityMapper <PaginaDTO, Pagina> {

    @Mapping(source = "capitulo.id", target = "capituloId")
    PaginaDTO toDto(Pagina pagina); 

    @Mapping(source = "capituloId", target = "capitulo")
    Pagina toEntity(PaginaDTO paginaDTO); 
    default Pagina fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pagina pagina = new Pagina();
        pagina.setId(id);
        return pagina;
    }
}
