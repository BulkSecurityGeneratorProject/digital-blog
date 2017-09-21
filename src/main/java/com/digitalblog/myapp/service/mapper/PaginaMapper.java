package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.PaginaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Pagina and its DTO PaginaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaginaMapper {

    @Mapping(source = "capitulo.id", target = "capituloId")
    PaginaDTO paginaToPaginaDTO(Pagina pagina);

    List<PaginaDTO> paginasToPaginaDTOs(List<Pagina> paginas);

    @Mapping(source = "capituloId", target = "capitulo")
    Pagina paginaDTOToPagina(PaginaDTO paginaDTO);

    List<Pagina> paginaDTOsToPaginas(List<PaginaDTO> paginaDTOs);

    default Capitulo capituloFromId(Long id) {
        if (id == null) {
            return null;
        }
        Capitulo capitulo = new Capitulo();
        capitulo.setId(id);
        return capitulo;
    }
}
