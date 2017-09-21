package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.ImagenPorPublicacionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ImagenPorPublicacion and its DTO ImagenPorPublicacionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImagenPorPublicacionMapper {

    ImagenPorPublicacionDTO imagenPorPublicacionToImagenPorPublicacionDTO(ImagenPorPublicacion imagenPorPublicacion);

    List<ImagenPorPublicacionDTO> imagenPorPublicacionsToImagenPorPublicacionDTOs(List<ImagenPorPublicacion> imagenPorPublicacions);

    ImagenPorPublicacion imagenPorPublicacionDTOToImagenPorPublicacion(ImagenPorPublicacionDTO imagenPorPublicacionDTO);

    List<ImagenPorPublicacion> imagenPorPublicacionDTOsToImagenPorPublicacions(List<ImagenPorPublicacionDTO> imagenPorPublicacionDTOs);
}
