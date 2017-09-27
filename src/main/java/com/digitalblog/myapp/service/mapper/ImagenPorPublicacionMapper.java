package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.ImagenPorPublicacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ImagenPorPublicacion and its DTO ImagenPorPublicacionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImagenPorPublicacionMapper extends EntityMapper <ImagenPorPublicacionDTO, ImagenPorPublicacion> {
    
    
    default ImagenPorPublicacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        ImagenPorPublicacion imagenPorPublicacion = new ImagenPorPublicacion();
        imagenPorPublicacion.setId(id);
        return imagenPorPublicacion;
    }
}
