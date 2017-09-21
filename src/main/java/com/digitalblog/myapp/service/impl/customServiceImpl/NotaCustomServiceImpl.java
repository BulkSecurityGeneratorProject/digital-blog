package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.repository.customRepository.NotaRepositoryCustom;
import com.digitalblog.myapp.service.customService.NotaServiceCustom;
import com.digitalblog.myapp.service.dto.NotaDTO;
import com.digitalblog.myapp.service.mapper.NotaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotaCustomServiceImpl implements NotaServiceCustom {

    private final Logger log = LoggerFactory.getLogger(NotaCustomServiceImpl.class);

    private final NotaRepositoryCustom notaRepositoryCustom;

    private final NotaMapper notaMapper;

    public NotaCustomServiceImpl(NotaRepositoryCustom notaRepositoryCustom, NotaMapper notaMapper) {
        this.notaRepositoryCustom = notaRepositoryCustom;
        this.notaMapper = notaMapper;
    }

    /**
     * @author Christopher Sullivan
     * obtiene las notas de la pagina de esa publicacion
     * @param idPagina
     * @return List<NotaDTO>
     * @version 1.0
     */
    @Transactional(readOnly = true)
    public List<NotaDTO> getNotaDePublicacion(Long idPagina){
        List<NotaDTO> result = notaRepositoryCustom.findByIdPagina(idPagina).stream()
            .map(notaMapper::notaToNotaDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }
}
