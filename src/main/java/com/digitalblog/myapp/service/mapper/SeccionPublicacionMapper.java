package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.SeccionPublicacionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SeccionPublicacion and its DTO SeccionPublicacionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SeccionPublicacionMapper {

    @Mapping(source = "idSeccionSP.id", target = "idSeccionSPId")
    @Mapping(source = "idPublicacionSP.id", target = "idPublicacionSPId")
    SeccionPublicacionDTO seccionPublicacionToSeccionPublicacionDTO(SeccionPublicacion seccionPublicacion);

    List<SeccionPublicacionDTO> seccionPublicacionsToSeccionPublicacionDTOs(List<SeccionPublicacion> seccionPublicacions);

    @Mapping(source = "idSeccionSPId", target = "idSeccionSP")
    @Mapping(source = "idPublicacionSPId", target = "idPublicacionSP")
    SeccionPublicacion seccionPublicacionDTOToSeccionPublicacion(SeccionPublicacionDTO seccionPublicacionDTO);

    List<SeccionPublicacion> seccionPublicacionDTOsToSeccionPublicacions(List<SeccionPublicacionDTO> seccionPublicacionDTOs);

    default Seccion seccionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Seccion seccion = new Seccion();
        seccion.setId(id);
        return seccion;
    }

    default Publicacion publicacionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Publicacion publicacion = new Publicacion();
        publicacion.setId(id);
        return publicacion;
    }
}
