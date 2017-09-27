package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.ComentarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comentario and its DTO ComentarioDTO.
 */
@Mapper(componentModel = "spring", uses = {RespuestaMapper.class, UsuarioMapper.class, PublicacionMapper.class, })
public interface ComentarioMapper extends EntityMapper <ComentarioDTO, Comentario> {

    @Mapping(source = "idComentarioRespuesta.id", target = "idComentarioRespuestaId")

    @Mapping(source = "idComentarioU.id", target = "idComentarioUId")

    @Mapping(source = "idComentarioP.id", target = "idComentarioPId")
    ComentarioDTO toDto(Comentario comentario); 

    @Mapping(source = "idComentarioRespuestaId", target = "idComentarioRespuesta")

    @Mapping(source = "idComentarioUId", target = "idComentarioU")

    @Mapping(source = "idComentarioPId", target = "idComentarioP")
    Comentario toEntity(ComentarioDTO comentarioDTO); 
    default Comentario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comentario comentario = new Comentario();
        comentario.setId(id);
        return comentario;
    }
}
