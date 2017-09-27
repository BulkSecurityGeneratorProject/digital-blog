package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.CapituloDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Capitulo and its DTO CapituloDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CapituloMapper {

    @Mapping(source = "capitulo.id", target = "capituloId")
    @Mapping(source = "idPublicacionC.id", target = "idPublicacionCId")
    CapituloDTO capituloToCapituloDTO(Capitulo capitulo);

    List<CapituloDTO> capitulosToCapituloDTOs(List<Capitulo> capitulos);

    @Mapping(source = "capituloId", target = "capitulo")
    @Mapping(source = "idPublicacionCId", target = "idPublicacionC")
    Capitulo capituloDTOToCapitulo(CapituloDTO capituloDTO);

    List<Capitulo> capituloDTOsToCapitulos(List<CapituloDTO> capituloDTOs);

    default Publicacion publicacionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Publicacion publicacion = new Publicacion();
        publicacion.setId(id);
        return publicacion;
    }
}
