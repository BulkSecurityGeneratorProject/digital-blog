package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.PublicacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Publicacion and its DTO PublicacionDTO.
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, CategoriaMapper.class, TemaMapper.class, })
public interface PublicacionMapper extends EntityMapper <PublicacionDTO, Publicacion> {

    @Mapping(source = "usuario.id", target = "usuarioId")

    @Mapping(source = "categoria.id", target = "categoriaId")

    @Mapping(source = "tema.id", target = "temaId")
    PublicacionDTO toDto(Publicacion publicacion); 

    @Mapping(source = "usuarioId", target = "usuario")

    @Mapping(source = "categoriaId", target = "categoria")

    @Mapping(source = "temaId", target = "tema")
    Publicacion toEntity(PublicacionDTO publicacionDTO); 
    default Publicacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Publicacion publicacion = new Publicacion();
        publicacion.setId(id);
        return publicacion;
    }
}
