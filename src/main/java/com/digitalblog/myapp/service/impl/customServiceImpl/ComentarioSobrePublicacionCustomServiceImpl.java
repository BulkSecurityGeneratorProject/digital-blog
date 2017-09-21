package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.repository.customRepository.ComentarioSobrePublicacionRepositoryCustom;
import com.digitalblog.myapp.service.customService.ComentarioSobrePublicacionCustomService;
import com.digitalblog.myapp.service.dto.ComentarioDTO;
import com.digitalblog.myapp.service.mapper.ComentarioMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComentarioSobrePublicacionCustomServiceImpl implements ComentarioSobrePublicacionCustomService {

    private final ComentarioSobrePublicacionRepositoryCustom comentarioSobrePublicacionRepositoryCustom;

    private final ComentarioMapper comentarioMapper;

    public ComentarioSobrePublicacionCustomServiceImpl(ComentarioSobrePublicacionRepositoryCustom comentarioSobrePublicacionRepositoryCustom, ComentarioMapper comentarioMapper) {
        this.comentarioSobrePublicacionRepositoryCustom = comentarioSobrePublicacionRepositoryCustom;
        this.comentarioMapper = comentarioMapper;
    }

    /**
     * @author Christopher Sullivan
     * obtiene todos los comentarios de la publicacion
     * @param idPublicacion
     * @return Lista de comentarios List<ComentarioDTO>
     * @version 1.0
     */
    @Transactional(readOnly = true)
    public List<ComentarioDTO> getComentarioSobrePublicacion(Long idPublicacion){
        List<ComentarioDTO> result = comentarioSobrePublicacionRepositoryCustom.findByIdPublicacion(idPublicacion).stream()
            .map(comentarioMapper::comentarioToComentarioDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }
}
