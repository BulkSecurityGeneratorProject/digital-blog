package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.LikeTDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LikeT and its DTO LikeTDTO.
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, PublicacionMapper.class, })
public interface LikeTMapper extends EntityMapper <LikeTDTO, LikeT> {

    @Mapping(source = "idUsuarioL.id", target = "idUsuarioLId")

    @Mapping(source = "idLikeP.id", target = "idLikePId")
    LikeTDTO toDto(LikeT likeT); 

    @Mapping(source = "idUsuarioLId", target = "idUsuarioL")

    @Mapping(source = "idLikePId", target = "idLikeP")
    LikeT toEntity(LikeTDTO likeTDTO); 
    default LikeT fromId(Long id) {
        if (id == null) {
            return null;
        }
        LikeT likeT = new LikeT();
        likeT.setId(id);
        return likeT;
    }
}
