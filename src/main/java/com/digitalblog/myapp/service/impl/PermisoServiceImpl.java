package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.PermisoService;
import com.digitalblog.myapp.domain.Permiso;
import com.digitalblog.myapp.repository.PermisoRepository;
import com.digitalblog.myapp.service.dto.PermisoDTO;
import com.digitalblog.myapp.service.mapper.PermisoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Permiso.
 */
@Service
@Transactional
public class PermisoServiceImpl implements PermisoService{

    private final Logger log = LoggerFactory.getLogger(PermisoServiceImpl.class);
    
    private final PermisoRepository permisoRepository;

    private final PermisoMapper permisoMapper;

    public PermisoServiceImpl(PermisoRepository permisoRepository, PermisoMapper permisoMapper) {
        this.permisoRepository = permisoRepository;
        this.permisoMapper = permisoMapper;
    }

    /**
     * Save a permiso.
     *
     * @param permisoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PermisoDTO save(PermisoDTO permisoDTO) {
        log.debug("Request to save Permiso : {}", permisoDTO);
        Permiso permiso = permisoMapper.permisoDTOToPermiso(permisoDTO);
        permiso = permisoRepository.save(permiso);
        PermisoDTO result = permisoMapper.permisoToPermisoDTO(permiso);
        return result;
    }

    /**
     *  Get all the permisos.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PermisoDTO> findAll() {
        log.debug("Request to get all Permisos");
        List<PermisoDTO> result = permisoRepository.findAll().stream()
            .map(permisoMapper::permisoToPermisoDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one permiso by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PermisoDTO findOne(Long id) {
        log.debug("Request to get Permiso : {}", id);
        Permiso permiso = permisoRepository.findOne(id);
        PermisoDTO permisoDTO = permisoMapper.permisoToPermisoDTO(permiso);
        return permisoDTO;
    }

    /**
     *  Delete the  permiso by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Permiso : {}", id);
        permisoRepository.delete(id);
    }
}
