package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.UsuarioDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Usuario and its DTO UsuarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsuarioMapper {

    @Mapping(source = "rol.id", target = "rolId")
    UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);

    List<UsuarioDTO> usuariosToUsuarioDTOs(List<Usuario> usuarios);

    @Mapping(target = "usuarios", ignore = true)
    @Mapping(source = "rolId", target = "rol")
    Usuario usuarioDTOToUsuario(UsuarioDTO usuarioDTO);

    List<Usuario> usuarioDTOsToUsuarios(List<UsuarioDTO> usuarioDTOs);

    default Rol rolFromId(Long id) {
        if (id == null) {
            return null;
        }
        Rol rol = new Rol();
        rol.setId(id);
        return rol;
    }
}
