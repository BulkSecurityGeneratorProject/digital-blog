package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.service.NotificacionService;
import com.digitalblog.myapp.domain.Notificacion;
import com.digitalblog.myapp.repository.NotificacionRepository;
import com.digitalblog.myapp.service.dto.NotificacionDTO;
import com.digitalblog.myapp.service.mapper.NotificacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Notificacion.
 */
@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService{

    private final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);

    private final NotificacionRepository notificacionRepository;

    private final NotificacionMapper notificacionMapper;

    public NotificacionServiceImpl(NotificacionRepository notificacionRepository, NotificacionMapper notificacionMapper) {
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
    }

    /**
     * Save a notificacion.
     *
     * @param notificacionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NotificacionDTO save(NotificacionDTO notificacionDTO) {
        log.debug("Request to save Notificacion : {}", notificacionDTO);
        Notificacion notificacion = notificacionMapper.notificacionDTOToNotificacion(notificacionDTO);
        notificacion = notificacionRepository.save(notificacion);
        NotificacionDTO result = notificacionMapper.notificacionToNotificacionDTO(notificacion);
        return result;
    }

    /**
     *  Get all the notificacions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> findAll() {
        log.debug("Request to get all Notificacions");
        List<NotificacionDTO> result = notificacionRepository.findAll().stream()
            .map(notificacionMapper::notificacionToNotificacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one notificacion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NotificacionDTO findOne(Long id) {
        log.debug("Request to get Notificacion : {}", id);
        Notificacion notificacion = notificacionRepository.findOne(id);
        NotificacionDTO notificacionDTO = notificacionMapper.notificacionToNotificacionDTO(notificacion);
        return notificacionDTO;
    }

    /**
     *  Delete the  notificacion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Notificacion : {}", id);
        notificacionRepository.delete(id);
    }
}
