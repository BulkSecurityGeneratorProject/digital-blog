package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.BibliotecaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Biblioteca and its DTO BibliotecaDTO.
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, })
public interface BibliotecaMapper extends EntityMapper <BibliotecaDTO, Biblioteca> {

    @Mapping(source = "idUsuarioB.id", target = "idUsuarioBId")
    BibliotecaDTO toDto(Biblioteca biblioteca); 

    @Mapping(source = "idUsuarioBId", target = "idUsuarioB")
    @Mapping(target = "idSeccionBS", ignore = true)
    Biblioteca toEntity(BibliotecaDTO bibliotecaDTO); 
    default Biblioteca fromId(Long id) {
        if (id == null) {
            return null;
        }
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId(id);
        return biblioteca;
    }
}
