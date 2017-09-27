package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.CoolaboradorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Coolaborador and its DTO CoolaboradorDTO.
 */
@Mapper(componentModel = "spring", uses = {CapituloMapper.class, PublicacionMapper.class, UsuarioMapper.class, })
public interface CoolaboradorMapper extends EntityMapper <CoolaboradorDTO, Coolaborador> {

    @Mapping(source = "capituloC.id", target = "capituloCId")

    @Mapping(source = "publicacion.id", target = "publicacionId")

    @Mapping(source = "idUsuario.id", target = "idUsuarioId")
    CoolaboradorDTO toDto(Coolaborador coolaborador); 

    @Mapping(source = "capituloCId", target = "capituloC")

    @Mapping(source = "publicacionId", target = "publicacion")

    @Mapping(source = "idUsuarioId", target = "idUsuario")
    Coolaborador toEntity(CoolaboradorDTO coolaboradorDTO); 
    default Coolaborador fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coolaborador coolaborador = new Coolaborador();
        coolaborador.setId(id);
        return coolaborador;
    }
}
