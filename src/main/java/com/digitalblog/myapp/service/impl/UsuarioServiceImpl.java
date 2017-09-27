package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.UsuarioService;
import com.digitalblog.myapp.domain.Usuario;
import com.digitalblog.myapp.repository.UsuarioRepository;
import com.digitalblog.myapp.service.dto.UsuarioDTO;
import com.digitalblog.myapp.service.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Usuario.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

    private final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    
    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Save a usuario.
     *
     * @param usuarioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        log.debug("Request to save Usuario : {}", usuarioDTO);
        Usuario usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        UsuarioDTO result = usuarioMapper.usuarioToUsuarioDTO(usuario);
        return result;
    }

    /**
     *  Get all the usuarios.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        log.debug("Request to get all Usuarios");
        List<UsuarioDTO> result = usuarioRepository.findAll().stream()
            .map(usuarioMapper::usuarioToUsuarioDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one usuario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        Usuario usuario = usuarioRepository.findOne(id);
        UsuarioDTO usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(usuario);
        return usuarioDTO;
    }

    /**
     *  Delete the  usuario by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.delete(id);
    }
}
