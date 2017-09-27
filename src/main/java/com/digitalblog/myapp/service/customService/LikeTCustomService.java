package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.domain.LikeT;
import com.digitalblog.myapp.service.dto.LikeTDTO;

import java.util.List;

/**
 * Service Interface for managing LikeT.
 */
public interface LikeTCustomService {

    LikeTDTO obtenerLikeDeUsuario(Long idUsuario,Long idPublicacion);

    List<LikeTDTO> obtenerLikesDeUsuario(Long idUsuario);

}
