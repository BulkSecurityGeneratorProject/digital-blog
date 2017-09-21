package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.SeccionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Seccion and its DTO SeccionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SeccionMapper {
    @Mapping(source = "biblioteca.id", target = "bibliotecaId")
    @Mapping(source = "seccion.nombre", target = "seccionName")
    SeccionDTO seccionToSeccionDTO(Seccion seccion);

    List<SeccionDTO> seccionsToSeccionDTOs(List<Seccion> seccions);

    @Mapping(source = "bibliotecaId", target = "biblioteca")
    Seccion seccionDTOToSeccion(SeccionDTO seccionDTO);

    List<Seccion> seccionDTOsToSeccions(List<SeccionDTO> seccionDTOs);

    default Biblioteca bibliotecaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId(id);
        return biblioteca;
    }
}
