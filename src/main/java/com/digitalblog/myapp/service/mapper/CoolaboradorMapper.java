package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.CoolaboradorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Coolaborador and its DTO CoolaboradorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoolaboradorMapper {

    @Mapping(source = "capituloC.id", target = "capituloCId")
    @Mapping(source = "publicacion.id", target = "publicacionId")
    @Mapping(source = "idUsuario.id", target = "idUsuarioId")
    CoolaboradorDTO coolaboradorToCoolaboradorDTO(Coolaborador coolaborador);

    List<CoolaboradorDTO> coolaboradorsToCoolaboradorDTOs(List<Coolaborador> coolaboradors);

    @Mapping(source = "capituloCId", target = "capituloC")
    @Mapping(source = "publicacionId", target = "publicacion")
    @Mapping(source = "idUsuarioId", target = "idUsuario")
    Coolaborador coolaboradorDTOToCoolaborador(CoolaboradorDTO coolaboradorDTO);

    List<Coolaborador> coolaboradorDTOsToCoolaboradors(List<CoolaboradorDTO> coolaboradorDTOs);

    default Capitulo capituloFromId(Long id) {
        if (id == null) {
            return null;
        }
        Capitulo capitulo = new Capitulo();
        capitulo.setId(id);
        return capitulo;
    }

    default Publicacion publicacionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Publicacion publicacion = new Publicacion();
        publicacion.setId(id);
        return publicacion;
    }

    default Usuario usuarioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
