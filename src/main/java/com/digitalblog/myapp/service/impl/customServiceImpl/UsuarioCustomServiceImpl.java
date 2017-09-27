package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.Usuario;
import com.digitalblog.myapp.repository.customRepository.UsuarioRepositoryCustom;
import com.digitalblog.myapp.service.customService.UsuarioCustomService;
import com.digitalblog.myapp.service.dto.UsuarioDTO;
import com.digitalblog.myapp.service.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioCustomServiceImpl implements UsuarioCustomService {


        private final Logger log = LoggerFactory.getLogger(UsuarioCustomServiceImpl.class);

        private final UsuarioRepositoryCustom usuarioRepository;

        private final UsuarioMapper usuarioMapper;

        private final UsuarioRepositoryCustom usuarioRepositoryCustom;

        public UsuarioCustomServiceImpl(UsuarioRepositoryCustom usuarioRepository, UsuarioMapper usuarioMapper, UsuarioRepositoryCustom usuarioRepositoryCustom) {
            this.usuarioRepository = usuarioRepository;
            this.usuarioMapper = usuarioMapper;
            this.usuarioRepositoryCustom = usuarioRepositoryCustom;
        }

    /**
     * @author Maureen Leon
     * Encuentra  el usuario segun el jhiuser  que genera Jhipster
     * @param id jhiUserId
     * @return UsuarioDTO
     * @version 1.0
     */
    @Transactional(readOnly = true)
    public UsuarioDTO findByJhiUserId(Long id) {
        log.debug("Request to get Usuario : {}", id);
        Usuario usuario = usuarioRepository.findByJhiUserId(id);
        UsuarioDTO usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(usuario);
        return usuarioDTO;
    }


    /**
     * @author Maureen Leon
     * retorna los usuarios que tienen un estado valido ya que si un usuario no ha
     * completado su informacion no deberia aparecer
     * @return List<UsuarioDTO>
     * @version 1.0
     */
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAllWithValidState() {
        List<UsuarioDTO> result = usuarioRepositoryCustom.getAllUserWithValidState(true).stream()
            .map(usuarioMapper::usuarioToUsuarioDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * @author Eduardo Guerrero
     * Metodo para traer la informacion del usuario y poder obtener el id del jhi-user
     * @param id
     * @return UsuarioDTO
     * @version 1.0
     */
    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO findJhiUserByIdUsuario(Integer id) {
        Usuario usuario = usuarioRepositoryCustom.findJhiUserPorIdUsuario(id);
        UsuarioDTO usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(usuario);
        return usuarioDTO;
    }

}
