package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.RolePermisoService;
import com.digitalblog.myapp.domain.RolePermiso;
import com.digitalblog.myapp.repository.RolePermisoRepository;
import com.digitalblog.myapp.service.dto.RolePermisoDTO;
import com.digitalblog.myapp.service.mapper.RolePermisoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RolePermiso.
 */
@Service
@Transactional
public class RolePermisoServiceImpl implements RolePermisoService{

    private final Logger log = LoggerFactory.getLogger(RolePermisoServiceImpl.class);

    private final RolePermisoRepository rolePermisoRepository;

    private final RolePermisoMapper rolePermisoMapper;

    public RolePermisoServiceImpl(RolePermisoRepository rolePermisoRepository, RolePermisoMapper rolePermisoMapper) {
        this.rolePermisoRepository = rolePermisoRepository;
        this.rolePermisoMapper = rolePermisoMapper;
    }

    /**
     * Save a rolePermiso.
     *
     * @param rolePermisoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RolePermisoDTO save(RolePermisoDTO rolePermisoDTO) {
        log.debug("Request to save RolePermiso : {}", rolePermisoDTO);
        RolePermiso rolePermiso = rolePermisoMapper.toEntity(rolePermisoDTO);
        rolePermiso = rolePermisoRepository.save(rolePermiso);
        return rolePermisoMapper.toDto(rolePermiso);
    }

    /**
     *  Get all the rolePermisos.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RolePermisoDTO> findAll() {
        log.debug("Request to get all RolePermisos");
        return rolePermisoRepository.findAll().stream()
            .map(rolePermisoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one rolePermiso by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RolePermisoDTO findOne(Long id) {
        log.debug("Request to get RolePermiso : {}", id);
        RolePermiso rolePermiso = rolePermisoRepository.findOne(id);
        return rolePermisoMapper.toDto(rolePermiso);
    }

    /**
     *  Delete the  rolePermiso by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RolePermiso : {}", id);
        rolePermisoRepository.delete(id);
    }
}
