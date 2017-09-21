package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.RespuestaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Respuesta and its DTO RespuestaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RespuestaMapper {

    @Mapping(source = "idComentarioR.id", target = "idComentarioRId")
    RespuestaDTO respuestaToRespuestaDTO(Respuesta respuesta);

    List<RespuestaDTO> respuestasToRespuestaDTOs(List<Respuesta> respuestas);

    @Mapping(source = "idComentarioRId", target = "idComentarioR")
    Respuesta respuestaDTOToRespuesta(RespuestaDTO respuestaDTO);

    List<Respuesta> respuestaDTOsToRespuestas(List<RespuestaDTO> respuestaDTOs);

    default Comentario comentarioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Comentario comentario = new Comentario();
        comentario.setId(id);
        return comentario;
    }
}
