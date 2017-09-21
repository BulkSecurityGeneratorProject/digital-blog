package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.PaginaService;
import com.digitalblog.myapp.domain.Pagina;
import com.digitalblog.myapp.repository.PaginaRepository;
import com.digitalblog.myapp.service.dto.PaginaDTO;
import com.digitalblog.myapp.service.mapper.PaginaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Pagina.
 */
@Service
@Transactional
public class PaginaServiceImpl implements PaginaService{

    private final Logger log = LoggerFactory.getLogger(PaginaServiceImpl.class);
    
    private final PaginaRepository paginaRepository;

    private final PaginaMapper paginaMapper;

    public PaginaServiceImpl(PaginaRepository paginaRepository, PaginaMapper paginaMapper) {
        this.paginaRepository = paginaRepository;
        this.paginaMapper = paginaMapper;
    }

    /**
     * Save a pagina.
     *
     * @param paginaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaginaDTO save(PaginaDTO paginaDTO) {
        log.debug("Request to save Pagina : {}", paginaDTO);
        Pagina pagina = paginaMapper.paginaDTOToPagina(paginaDTO);
        pagina = paginaRepository.save(pagina);
        PaginaDTO result = paginaMapper.paginaToPaginaDTO(pagina);
        return result;
    }

    /**
     *  Get all the paginas.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaginaDTO> findAll() {
        log.debug("Request to get all Paginas");
        List<PaginaDTO> result = paginaRepository.findAll().stream()
            .map(paginaMapper::paginaToPaginaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one pagina by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PaginaDTO findOne(Long id) {
        log.debug("Request to get Pagina : {}", id);
        Pagina pagina = paginaRepository.findOne(id);
        PaginaDTO paginaDTO = paginaMapper.paginaToPaginaDTO(pagina);
        return paginaDTO;
    }

    /**
     *  Delete the  pagina by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pagina : {}", id);
        paginaRepository.delete(id);
    }
}
