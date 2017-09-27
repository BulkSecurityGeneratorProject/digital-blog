package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.ComentarioDTO;

import java.util.List;


public interface ComentarioSobrePublicacionCustomService {
    List<ComentarioDTO> getComentarioSobrePublicacion(Long idPublicacion);
}
