package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.PublicacionReportadaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PublicacionReportada and its DTO PublicacionReportadaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PublicacionReportadaMapper extends EntityMapper <PublicacionReportadaDTO, PublicacionReportada> {
    
    
    default PublicacionReportada fromId(Long id) {
        if (id == null) {
            return null;
        }
        PublicacionReportada publicacionReportada = new PublicacionReportada();
        publicacionReportada.setId(id);
        return publicacionReportada;
    }
}
