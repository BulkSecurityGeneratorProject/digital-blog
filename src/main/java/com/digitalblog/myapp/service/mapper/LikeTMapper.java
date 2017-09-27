package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.LikeTDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LikeT and its DTO LikeTDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LikeTMapper {

    @Mapping(source = "idUsuarioL.id", target = "idUsuarioLId")
    @Mapping(source = "idLikeP.id", target = "idLikePId")
    LikeTDTO likeTToLikeTDTO(LikeT likeT);

    List<LikeTDTO> likeTSToLikeTDTOs(List<LikeT> likeTS);

    @Mapping(source = "idUsuarioLId", target = "idUsuarioL")
    @Mapping(source = "idLikePId", target = "idLikeP")
    LikeT likeTDTOToLikeT(LikeTDTO likeTDTO);

    List<LikeT> likeTDTOsToLikeTS(List<LikeTDTO> likeTDTOs);

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
