package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.PublicacionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Publicacion and its DTO PublicacionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PublicacionMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "tema.id", target = "temaId")
    @Mapping(source = "usuario.fotoperfilContentType",target = "fotoPublicador")
    PublicacionDTO publicacionToPublicacionDTO(Publicacion publicacion);

    List<PublicacionDTO> publicacionsToPublicacionDTOs(List<Publicacion> publicacions);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "categoriaId", target = "categoria")
    @Mapping(source = "temaId", target = "tema")
    Publicacion publicacionDTOToPublicacion(PublicacionDTO publicacionDTO);

    List<Publicacion> publicacionDTOsToPublicacions(List<PublicacionDTO> publicacionDTOs);

    default Usuario usuarioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    default Categoria categoriaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(id);
        return categoria;
    }

    default Tema temaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Tema tema = new Tema();
        tema.setId(id);
        return tema;
    }
}
