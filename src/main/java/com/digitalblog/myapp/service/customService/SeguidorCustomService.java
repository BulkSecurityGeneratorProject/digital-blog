package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.SeguidorDTO;

import java.util.List;


public interface SeguidorCustomService {

    /**
     * Obtiene todos los seguidores de un usuario
     * findAllByUserId(Long id)
     * @param id del jhiuser
     * @return List<SeguidorDTO>
     */
    List<SeguidorDTO> findAllByUserId(Long id);

    /**
     * Obtiene todos los seguidos de un usuario
     * findAllSeguidosByUserId(Long id)
     * @param id del jhiuser
     * @return UsuarioDTO
     */
    List<SeguidorDTO> findAllSeguidosByUserId(Long id);

    /**
     * findSeguimientoByUsersId(Long seguido,Long seguidor)
     * @param seguido del seguido
     * @param seguido del seguidor
     * @return SeguidorDTO
     */
    SeguidorDTO findSeguimientoByUsersId(Long seguido,Long seguidor);
}
