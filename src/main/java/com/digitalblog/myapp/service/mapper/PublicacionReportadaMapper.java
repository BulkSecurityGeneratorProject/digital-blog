package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.PublicacionReportadaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PublicacionReportada and its DTO PublicacionReportadaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PublicacionReportadaMapper {

    PublicacionReportadaDTO publicacionReportadaToPublicacionReportadaDTO(PublicacionReportada publicacionReportada);

    List<PublicacionReportadaDTO> publicacionReportadasToPublicacionReportadaDTOs(List<PublicacionReportada> publicacionReportadas);

    PublicacionReportada publicacionReportadaDTOToPublicacionReportada(PublicacionReportadaDTO publicacionReportadaDTO);

    List<PublicacionReportada> publicacionReportadaDTOsToPublicacionReportadas(List<PublicacionReportadaDTO> publicacionReportadaDTOs);
}
