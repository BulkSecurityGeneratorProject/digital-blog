package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.domain.Notificacion;
import com.digitalblog.myapp.domain.Seguidor;
import com.digitalblog.myapp.repository.NotificacionRepository;
import com.digitalblog.myapp.repository.SeguidorRepository;
import com.digitalblog.myapp.service.SeguidorService;
import com.digitalblog.myapp.service.dto.NotificacionDTO;
import com.digitalblog.myapp.service.dto.SeguidorDTO;
import com.digitalblog.myapp.service.mapper.NotificacionMapper;
import com.digitalblog.myapp.service.mapper.SeguidorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Seguidor.
 */
@Service
@Transactional
public class SeguidorServiceImpl implements SeguidorService{

    private final Logger log = LoggerFactory.getLogger(SeguidorServiceImpl.class);

    private final SeguidorRepository seguidorRepository;

    private final SeguidorMapper seguidorMapper;

    private final SimpMessageSendingOperations messagingTemplate;

    private final NotificacionRepository notificacionRepository;

    private final NotificacionMapper notificacionMapper;

    public SeguidorServiceImpl(SeguidorRepository seguidorRepository, SeguidorMapper seguidorMapper, SimpMessageSendingOperations messagingTemplate, NotificacionRepository notificacionRepository, NotificacionMapper notificacionMapper) {
        this.seguidorRepository = seguidorRepository;
        this.seguidorMapper = seguidorMapper;
        this.messagingTemplate = messagingTemplate;
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
    }

    /**
     * Save a seguidor.
     *
     * @param seguidorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeguidorDTO save(SeguidorDTO seguidorDTO) {
        log.debug("Request to save Seguidor : {}", seguidorDTO);
        Seguidor seguidor = seguidorMapper.seguidorDTOToSeguidor(seguidorDTO);
        seguidor = seguidorRepository.save(seguidor);
        SeguidorDTO result = seguidorMapper.seguidorToSeguidorDTO(seguidor);

        /**
         * creo la notificacion
         * notifica al seguido con el topic personal
         */

        NotificacionDTO notificacionDTO=new NotificacionDTO();
        notificacionDTO.setDescripcion("Un nuevo publicador te ha seguido");
        notificacionDTO.setTipo("Seguidor");
        notificacionDTO.setEstado(false);
        notificacionDTO.setIdUsuario(Math.toIntExact(result.getIdSeguidoId()));
        notificacionDTO.setLink(result.getIdSeguidorId().toString());

        messagingTemplate.convertAndSend("/topic/" + Math.toIntExact(result.getIdSeguidoId()), notificacionDTO);

        Notificacion notificacion = notificacionMapper.notificacionDTOToNotificacion(notificacionDTO);
        notificacion = notificacionRepository.save(notificacion);

        return result;
    }

    /**
     *  Get all the seguidors.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SeguidorDTO> findAll() {
        log.debug("Request to get all Seguidors");
        List<SeguidorDTO> result = seguidorRepository.findAll().stream()
            .map(seguidorMapper::seguidorToSeguidorDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one seguidor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeguidorDTO findOne(Long id) {
        log.debug("Request to get Seguidor : {}", id);
        Seguidor seguidor = seguidorRepository.findOne(id);
        SeguidorDTO seguidorDTO = seguidorMapper.seguidorToSeguidorDTO(seguidor);
        return seguidorDTO;
    }

    /**
     *  Delete the  seguidor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seguidor : {}", id);

        SeguidorDTO seguido = findOne(id);
        NotificacionDTO notificacionDTO=new NotificacionDTO();
        notificacionDTO.setIdUsuario(0);
        messagingTemplate.convertAndSend("/topic/" + Math.toIntExact(seguido.getIdSeguidoId()), notificacionDTO);
        seguidorRepository.delete(id);
    }
}
