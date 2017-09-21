package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.LikeT;
import com.digitalblog.myapp.repository.LikeTRepository;
import com.digitalblog.myapp.repository.customRepository.LikeTRepositoryCustom;
import com.digitalblog.myapp.service.LikeTService;
import com.digitalblog.myapp.service.customService.LikeTCustomService;
import com.digitalblog.myapp.service.dto.LikeTDTO;
import com.digitalblog.myapp.service.mapper.LikeTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LikeT.
 */
@Service
@Transactional
public class LikeTCustomServiceImpl implements LikeTCustomService{

    private final Logger log = LoggerFactory.getLogger(LikeTCustomServiceImpl.class);

    private final LikeTRepositoryCustom likeTRepositoryCustom;

    private final LikeTMapper likeTMapper;

    public LikeTCustomServiceImpl(LikeTRepositoryCustom likeTRepositoryCustom, LikeTMapper likeTMapper) {
        this.likeTRepositoryCustom = likeTRepositoryCustom;
        this.likeTMapper = likeTMapper;

    }

    /**
     * @author Eduardo Guerrero
     * Obtiene un like del usuario
     * @param idUsuario
     * @param idPublicacion
     * @return LikeTDTO
     * @version 1.0
     */
    @Override
    @Transactional(readOnly = true)
    public LikeTDTO obtenerLikeDeUsuario(Long idUsuario, Long idPublicacion) {
        LikeT likeT = likeTRepositoryCustom.findLikeByUser(idUsuario,idPublicacion);
        LikeTDTO likeTDTO = likeTMapper.likeTToLikeTDTO(likeT);
        return likeTDTO;
    }

    /**
     * @author Eduardo Guerrero
     * obtiene los likes totales del usuario
     * @param idUsuario
     * @return Lista de likes
     * @version 1.0
     */
    @Override
    @Transactional(readOnly = true)
    public List<LikeTDTO> obtenerLikesDeUsuario(Long idUsuario) {
        List<LikeTDTO> result = likeTRepositoryCustom.findLikesByUser(idUsuario).stream()
            .map(likeTMapper::likeTToLikeTDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

}
