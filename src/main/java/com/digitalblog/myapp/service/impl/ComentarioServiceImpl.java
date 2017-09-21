package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.domain.Notificacion;
import com.digitalblog.myapp.repository.NotificacionRepository;
import com.digitalblog.myapp.repository.customRepository.PublicacionRepositoryCustom;
import com.digitalblog.myapp.service.ComentarioService;
import com.digitalblog.myapp.domain.Comentario;
import com.digitalblog.myapp.repository.ComentarioRepository;
import com.digitalblog.myapp.service.dto.ComentarioDTO;
import com.digitalblog.myapp.service.dto.NotificacionDTO;
import com.digitalblog.myapp.service.mapper.ComentarioMapper;
import com.digitalblog.myapp.service.mapper.NotificacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Comentario.
 */
@Service
@Transactional
public class ComentarioServiceImpl implements ComentarioService{

    private final Logger log = LoggerFactory.getLogger(ComentarioServiceImpl.class);

    private final ComentarioRepository comentarioRepository;

    private final ComentarioMapper comentarioMapper;

    private final PublicacionRepositoryCustom publicacionRepository;

    private final SimpMessageSendingOperations messagingTemplate;

    private final NotificacionRepository notificacionRepository;

    private final NotificacionMapper notificacionMapper;

    public ComentarioServiceImpl(ComentarioRepository comentarioRepository, ComentarioMapper comentarioMapper, SimpMessageSendingOperations messagingTemplate,
                                 NotificacionRepository notificacionRepository, NotificacionMapper notificacionMapper,PublicacionRepositoryCustom publicacionRepository) {
        this.comentarioRepository = comentarioRepository;
        this.comentarioMapper = comentarioMapper;
        this.publicacionRepository = publicacionRepository;
        this.messagingTemplate = messagingTemplate;
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
    }


    /**
     * Save a comentario.
     *
     * @param comentarioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ComentarioDTO save(ComentarioDTO comentarioDTO) {


        log.debug("Request to save Comentario : {}", comentarioDTO);
        Comentario comentario = comentarioMapper.comentarioDTOToComentario(comentarioDTO);
        comentario = comentarioRepository.save(comentario);
        ComentarioDTO result = comentarioMapper.comentarioToComentarioDTO(comentario);

        if(comentarioDTO.getId()==null){
            Long idDuenioPublicacion = publicacionRepository.findIdUsuarioByIdPublicacion(result.getIdComentarioPId());

            NotificacionDTO notificacionDTO=new NotificacionDTO();
            notificacionDTO.setDescripcion("Nuevo comentario a su publicación");
            notificacionDTO.setTipo("Publicacion");
            notificacionDTO.setEstado(false);
            notificacionDTO.setIdUsuario(Math.toIntExact(idDuenioPublicacion));
            notificacionDTO.setLink(result.getIdComentarioPId().toString());

            messagingTemplate.convertAndSend("/topic/" + Math.toIntExact(idDuenioPublicacion), notificacionDTO);

            Notificacion notificacion = notificacionMapper.notificacionDTOToNotificacion(notificacionDTO);
            notificacion = notificacionRepository.save(notificacion);
        }

        return result;
    }

    /**
     *  Get all the comentarios.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ComentarioDTO> findAll() {
        log.debug("Request to get all Comentarios");
        List<ComentarioDTO> result = comentarioRepository.findAll().stream()
            .map(comentarioMapper::comentarioToComentarioDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one comentario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ComentarioDTO findOne(Long id) {
        log.debug("Request to get Comentario : {}", id);
        Comentario comentario = comentarioRepository.findOne(id);
        ComentarioDTO comentarioDTO = comentarioMapper.comentarioToComentarioDTO(comentario);
        return comentarioDTO;
    }

    /**
     *  Delete the  comentario by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comentario : {}", id);
        comentarioRepository.delete(id);
    }
}
