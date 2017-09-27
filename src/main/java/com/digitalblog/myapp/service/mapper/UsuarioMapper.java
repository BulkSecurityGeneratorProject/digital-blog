package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.UsuarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Usuario and its DTO UsuarioDTO.
 */
@Mapper(componentModel = "spring", uses = {RolMapper.class, })
public interface UsuarioMapper extends EntityMapper <UsuarioDTO, Usuario> {

    @Mapping(source = "rol.id", target = "rolId")
    UsuarioDTO toDto(Usuario usuario); 
    @Mapping(target = "usuarios", ignore = true)

    @Mapping(source = "rolId", target = "rol")
    Usuario toEntity(UsuarioDTO usuarioDTO); 
    default Usuario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
