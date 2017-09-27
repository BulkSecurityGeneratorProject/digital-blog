package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.ComentarioDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Comentario and its DTO ComentarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ComentarioMapper {

    @Mapping(source = "idComentarioRespuesta.id", target = "idComentarioRespuestaId")
    @Mapping(source = "idComentarioU.id", target = "idComentarioUId")
    @Mapping(source = "idComentarioP.id", target = "idComentarioPId")
    @Mapping(source = "idComentarioRespuesta.contenido", target= "respuesta")
    @Mapping(source = "idComentarioU.fotoperfilContentType", target = "fotoComentador")
    ComentarioDTO comentarioToComentarioDTO(Comentario comentario);

    List<ComentarioDTO> comentariosToComentarioDTOs(List<Comentario> comentarios);

    @Mapping(source = "idComentarioRespuestaId", target = "idComentarioRespuesta")
    @Mapping(source = "idComentarioUId", target = "idComentarioU")
    @Mapping(source = "idComentarioPId", target = "idComentarioP")
    Comentario comentarioDTOToComentario(ComentarioDTO comentarioDTO);

    List<Comentario> comentarioDTOsToComentarios(List<ComentarioDTO> comentarioDTOs);

    default Respuesta respuestaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Respuesta respuesta = new Respuesta();
        respuesta.setId(id);
        return respuesta;
    }

    default Usuario usuarioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    default Publicacion publicacionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Publicacion publicacion = new Publicacion();
        publicacion.setId(id);
        return publicacion;
    }
}
