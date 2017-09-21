package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.NotaDTO;
import java.util.List;

public interface NotaServiceCustom {

    List<NotaDTO> getNotaDePublicacion(Long idPagina);
}
