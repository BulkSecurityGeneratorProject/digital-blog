package com.digitalblog.myapp.service.impl;

import com.digitalblog.myapp.domain.LikeT;
import com.digitalblog.myapp.domain.Notificacion;
import com.digitalblog.myapp.repository.LikeTRepository;
import com.digitalblog.myapp.repository.NotificacionRepository;
import com.digitalblog.myapp.repository.customRepository.PublicacionRepositoryCustom;
import com.digitalblog.myapp.service.LikeTService;
import com.digitalblog.myapp.service.dto.LikeTDTO;
import com.digitalblog.myapp.service.dto.NotificacionDTO;
import com.digitalblog.myapp.service.mapper.LikeTMapper;
import com.digitalblog.myapp.service.mapper.NotificacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LikeT.
 */
@Service
@Transactional
public class LikeTServiceImpl implements LikeTService{

    private final Logger log = LoggerFactory.getLogger(LikeTServiceImpl.class);

    private final LikeTRepository likeTRepository;

    private final LikeTMapper likeTMapper;

    private final PublicacionRepositoryCustom publicacionRepository;

    private final SimpMessageSendingOperations messagingTemplate;

    private final NotificacionRepository notificacionRepository;

    private final NotificacionMapper notificacionMapper;

    public LikeTServiceImpl(LikeTRepository likeTRepository, LikeTMapper likeTMapper, SimpMessageSendingOperations messagingTemplate,
            NotificacionRepository notificacionRepository, NotificacionMapper notificacionMapper,PublicacionRepositoryCustom publicacionRepository) {
        this.likeTRepository = likeTRepository;
        this.likeTMapper = likeTMapper;
        this.publicacionRepository = publicacionRepository;
        this.messagingTemplate = messagingTemplate;
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
    }

    /**
     * Save a likeT.
     *
     * @param likeTDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LikeTDTO save(LikeTDTO likeTDTO) {
        log.debug("Request to save LikeT : {}", likeTDTO);
        LikeT likeT = likeTMapper.likeTDTOToLikeT(likeTDTO);
        likeT = likeTRepository.save(likeT);
        LikeTDTO result = likeTMapper.likeTToLikeTDTO(likeT);

        Long idDuenioPublicacion = publicacionRepository.findIdUsuarioByIdPublicacion(result.getIdLikePId());

            NotificacionDTO notificacionDTO=new NotificacionDTO();
            notificacionDTO.setDescripcion("Nuevo like a su publicación");
            notificacionDTO.setTipo("Publicacion");
            notificacionDTO.setEstado(false);
            notificacionDTO.setIdUsuario(Math.toIntExact(idDuenioPublicacion));
            notificacionDTO.setLink(result.getIdLikePId().toString());

            messagingTemplate.convertAndSend("/topic/" + Math.toIntExact(idDuenioPublicacion), notificacionDTO);

            Notificacion notificacion = notificacionMapper.notificacionDTOToNotificacion(notificacionDTO);
            notificacion = notificacionRepository.save(notificacion);

        return result;

    }

    /**
     *  Get all the likeTS.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LikeTDTO> findAll() {
        log.debug("Request to get all LikeTS");
        List<LikeTDTO> result = likeTRepository.findAll().stream()
            .map(likeTMapper::likeTToLikeTDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one likeT by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LikeTDTO findOne(Long id) {
        log.debug("Request to get LikeT : {}", id);
        LikeT likeT = likeTRepository.findOne(id);
        LikeTDTO likeTDTO = likeTMapper.likeTToLikeTDTO(likeT);
        return likeTDTO;
    }

    /**
     *  Delete the  likeT by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LikeT : {}", id);
        likeTRepository.delete(id);
    }
}
