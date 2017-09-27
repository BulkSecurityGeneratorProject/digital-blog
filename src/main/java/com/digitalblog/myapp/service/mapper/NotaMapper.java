package com.digitalblog.myapp.service.mapper;

import com.digitalblog.myapp.domain.*;
import com.digitalblog.myapp.service.dto.NotaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Nota and its DTO NotaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NotaMapper {

    @Mapping(source = "paginaNota.id", target = "paginaNotaId")
    NotaDTO notaToNotaDTO(Nota nota);

    List<NotaDTO> notasToNotaDTOs(List<Nota> notas);

    @Mapping(source = "paginaNotaId", target = "paginaNota")
    Nota notaDTOToNota(NotaDTO notaDTO);

    List<Nota> notaDTOsToNotas(List<NotaDTO> notaDTOs);

    default Pagina paginaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Pagina pagina = new Pagina();
        pagina.setId(id);
        return pagina;
    }
}
