package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.TemaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Tema and its DTO TemaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TemaMapper {

    TemaDTO temaToTemaDTO(Tema tema);

    List<TemaDTO> temasToTemaDTOs(List<Tema> temas);

    Tema temaDTOToTema(TemaDTO temaDTO);

    List<Tema> temaDTOsToTemas(List<TemaDTO> temaDTOs);
}
