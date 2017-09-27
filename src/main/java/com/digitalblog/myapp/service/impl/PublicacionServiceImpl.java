package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.domain.Notificacion;
import com.digitalblog.myapp.domain.Publicacion;
import com.digitalblog.myapp.repository.NotificacionRepository;
import com.digitalblog.myapp.repository.PublicacionRepository;
import com.digitalblog.myapp.repository.customRepository.SeguidorRepositoryCustom;
import com.digitalblog.myapp.service.PublicacionService;
import com.digitalblog.myapp.service.dto.NotificacionDTO;
import com.digitalblog.myapp.service.dto.PublicacionDTO;
import com.digitalblog.myapp.service.dto.SeguidorDTO;
import com.digitalblog.myapp.service.mapper.NotificacionMapper;
import com.digitalblog.myapp.service.mapper.PublicacionMapper;
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
 * Service Implementation for managing Publicacion.
 */
@Service
@Transactional
public class PublicacionServiceImpl implements PublicacionService{

    private final Logger log = LoggerFactory.getLogger(PublicacionServiceImpl.class);

    private final PublicacionRepository publicacionRepository;

    private final PublicacionMapper publicacionMapper;

    private final SimpMessageSendingOperations messagingTemplate;

    private final NotificacionRepository notificacionRepository;

    private final NotificacionMapper notificacionMapper;

    private final SeguidorRepositoryCustom seguidorRepository;

    private final SeguidorMapper seguidorMapper;


    public PublicacionServiceImpl(PublicacionRepository publicacionRepository, PublicacionMapper publicacionMapper, SimpMessageSendingOperations messagingTemplate, NotificacionRepository notificacionRepository, NotificacionMapper notificacionMapper,SeguidorRepositoryCustom seguidorRepository, SeguidorMapper seguidorMapper) {
        this.publicacionRepository = publicacionRepository;
        this.publicacionMapper = publicacionMapper;
        this.messagingTemplate = messagingTemplate;
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
        this.seguidorRepository = seguidorRepository;
        this.seguidorMapper = seguidorMapper;
    }

    /**
     * Save a publicacion.
     *
     * @param publicacionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PublicacionDTO save(PublicacionDTO publicacionDTO) {
        log.debug("Request to save Publicacion : {}", publicacionDTO);
        Publicacion publicacion = publicacionMapper.publicacionDTOToPublicacion(publicacionDTO);
        publicacion = publicacionRepository.save(publicacion);
        PublicacionDTO result = publicacionMapper.publicacionToPublicacionDTO(publicacion);

        if(publicacionDTO.getEstado()==2){
            List<SeguidorDTO> listaSeguidores = findAllByUserId(result.getUsuarioId());

            for(int i=0;i<listaSeguidores.size();i++){
                Long idSeguidor = listaSeguidores.get(i).getIdSeguidorId();

                NotificacionDTO notificacionDTO=new NotificacionDTO();
                notificacionDTO.setDescripcion("Nueva publicación");
                notificacionDTO.setTipo("Publicacion");
                notificacionDTO.setEstado(false);
                notificacionDTO.setIdUsuario(Math.toIntExact(idSeguidor));
                notificacionDTO.setLink(result.getId().toString());

                messagingTemplate.convertAndSend("/topic/" + Math.toIntExact(idSeguidor), notificacionDTO);

                Notificacion notificacion = notificacionMapper.notificacionDTOToNotificacion(notificacionDTO);
                notificacion = notificacionRepository.save(notificacion);
            }
        }
        return result;
    }

    private List<SeguidorDTO> findAllByUserId(Long usuarioId) {

        List<SeguidorDTO> result = seguidorRepository.findAllByUserId(usuarioId).stream()
            .map(seguidorMapper::seguidorToSeguidorDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }


    /**
     *  Get all the publicacions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PublicacionDTO> findAll() {
        log.debug("Request to get all Publicacions");
        List<PublicacionDTO> result = publicacionRepository.findAll().stream()
            .map(publicacionMapper::publicacionToPublicacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one publicacion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PublicacionDTO findOne(Long id) {
        log.debug("Request to get Publicacion : {}", id);
        Publicacion publicacion = publicacionRepository.findOne(id);
        PublicacionDTO publicacionDTO = publicacionMapper.publicacionToPublicacionDTO(publicacion);
        return publicacionDTO;
    }

    /**
     *  Delete the  publicacion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Publicacion : {}", id);
        publicacionRepository.delete(id);
    }
}
