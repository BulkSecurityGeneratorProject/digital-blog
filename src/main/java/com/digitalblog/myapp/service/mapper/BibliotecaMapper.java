package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.BibliotecaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Biblioteca and its DTO BibliotecaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BibliotecaMapper {

    @Mapping(source = "idUsuarioB.id", target = "idUsuarioBId")
    BibliotecaDTO bibliotecaToBibliotecaDTO(Biblioteca biblioteca);

    List<BibliotecaDTO> bibliotecasToBibliotecaDTOs(List<Biblioteca> bibliotecas);

    @Mapping(source = "idUsuarioBId", target = "idUsuarioB")
    @Mapping(target = "idSeccionBS", ignore = true)
    Biblioteca bibliotecaDTOToBiblioteca(BibliotecaDTO bibliotecaDTO);

    List<Biblioteca> bibliotecaDTOsToBibliotecas(List<BibliotecaDTO> bibliotecaDTOs);

    default Usuario usuarioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
