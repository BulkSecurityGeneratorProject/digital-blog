package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.SeccionPublicacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SeccionPublicacion and its DTO SeccionPublicacionDTO.
 */
@Mapper(componentModel = "spring", uses = {SeccionMapper.class, PublicacionMapper.class, })
public interface SeccionPublicacionMapper extends EntityMapper <SeccionPublicacionDTO, SeccionPublicacion> {

    @Mapping(source = "idSeccionSP.id", target = "idSeccionSPId")

    @Mapping(source = "idPublicacionSP.id", target = "idPublicacionSPId")
    SeccionPublicacionDTO toDto(SeccionPublicacion seccionPublicacion); 

    @Mapping(source = "idSeccionSPId", target = "idSeccionSP")

    @Mapping(source = "idPublicacionSPId", target = "idPublicacionSP")
    SeccionPublicacion toEntity(SeccionPublicacionDTO seccionPublicacionDTO); 
    default SeccionPublicacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        SeccionPublicacion seccionPublicacion = new SeccionPublicacion();
        seccionPublicacion.setId(id);
        return seccionPublicacion;
    }
}
