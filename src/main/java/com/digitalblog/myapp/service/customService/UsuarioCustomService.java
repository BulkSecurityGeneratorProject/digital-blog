package com.digitalblog.myapp.service.customService;

import com.digitalblog.myapp.service.dto.UsuarioDTO;

import java.util.List;

/**
 * Created by Maureen on 3/7/2017.
 */
public interface UsuarioCustomService {

    /**
     * findByJhiUserId(Long id) en cuentra el usuario
     * @param id del jhiuser
     * @return UsuarioDTO
     */
    UsuarioDTO findByJhiUserId(Long id);

    /**
     * Obtiene todos los usuarios con estado valido
     * findAllWithValidState(Long id)
     * @return List<UsuarioDTO>
     */
    List<UsuarioDTO> findAllWithValidState();

    /**
     * Obtiene el id del jhi-user del usuario norma.
     * @param id
     * @return
     */
    UsuarioDTO findJhiUserByIdUsuario(Integer id);
}
