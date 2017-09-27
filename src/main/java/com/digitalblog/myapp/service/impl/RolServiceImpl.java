package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.RolService;
import com.digitalblog.myapp.domain.Rol;
import com.digitalblog.myapp.repository.RolRepository;
import com.digitalblog.myapp.service.dto.RolDTO;
import com.digitalblog.myapp.service.mapper.RolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Rol.
 */
@Service
@Transactional
public class RolServiceImpl implements RolService{

    private final Logger log = LoggerFactory.getLogger(RolServiceImpl.class);

    private final RolRepository rolRepository;

    private final RolMapper rolMapper;

    public RolServiceImpl(RolRepository rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    /**
     * Save a rol.
     *
     * @param rolDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RolDTO save(RolDTO rolDTO) {
        log.debug("Request to save Rol : {}", rolDTO);
        Rol rol = rolMapper.toEntity(rolDTO);
        rol = rolRepository.save(rol);
        return rolMapper.toDto(rol);
    }

    /**
     *  Get all the rols.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RolDTO> findAll() {
        log.debug("Request to get all Rols");
        return rolRepository.findAll().stream()
            .map(rolMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one rol by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RolDTO findOne(Long id) {
        log.debug("Request to get Rol : {}", id);
        Rol rol = rolRepository.findOne(id);
        return rolMapper.toDto(rol);
    }

    /**
     *  Delete the  rol by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rol : {}", id);
        rolRepository.delete(id);
    }
}
