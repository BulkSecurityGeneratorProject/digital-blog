package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.RespuestaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Respuesta and its DTO RespuestaDTO.
 */
@Mapper(componentModel = "spring", uses = {ComentarioMapper.class, })
public interface RespuestaMapper extends EntityMapper <RespuestaDTO, Respuesta> {

    @Mapping(source = "idComentarioR.id", target = "idComentarioRId")
    RespuestaDTO toDto(Respuesta respuesta); 

    @Mapping(source = "idComentarioRId", target = "idComentarioR")
    Respuesta toEntity(RespuestaDTO respuestaDTO); 
    default Respuesta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Respuesta respuesta = new Respuesta();
        respuesta.setId(id);
        return respuesta;
    }
}
