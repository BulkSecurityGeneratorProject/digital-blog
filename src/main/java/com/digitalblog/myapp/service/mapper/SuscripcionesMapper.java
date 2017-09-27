package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.SuscripcionesDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Suscripciones and its DTO SuscripcionesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SuscripcionesMapper {

    SuscripcionesDTO suscripcionesToSuscripcionesDTO(Suscripciones suscripciones);

    List<SuscripcionesDTO> suscripcionesToSuscripcionesDTOs(List<Suscripciones> suscripciones);

    Suscripciones suscripcionesDTOToSuscripciones(SuscripcionesDTO suscripcionesDTO);

    List<Suscripciones> suscripcionesDTOsToSuscripciones(List<SuscripcionesDTO> suscripcionesDTOs);
}
