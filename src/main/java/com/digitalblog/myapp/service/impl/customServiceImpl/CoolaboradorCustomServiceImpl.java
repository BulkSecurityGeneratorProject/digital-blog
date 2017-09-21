package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.Capitulo;
import com.digitalblog.myapp.domain.Coolaborador;
import com.digitalblog.myapp.domain.Notificacion;
import com.digitalblog.myapp.repository.CapituloRepository;
import com.digitalblog.myapp.repository.NotificacionRepository;
import com.digitalblog.myapp.repository.customRepository.CoolaboradorRepositoryCustom;
import com.digitalblog.myapp.service.customService.CoolaboradorCustomService;
import com.digitalblog.myapp.service.dto.CapituloDTO;
import com.digitalblog.myapp.service.dto.CoolaboradorDTO;
import com.digitalblog.myapp.service.dto.NotificacionDTO;
import com.digitalblog.myapp.service.mapper.CapituloMapper;
import com.digitalblog.myapp.service.mapper.CoolaboradorMapper;
import com.digitalblog.myapp.service.mapper.NotificacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jose_ on 13/4/2017.
 */
@Service
@Transactional
public class CoolaboradorCustomServiceImpl implements CoolaboradorCustomService{

    private final Logger log = LoggerFactory.getLogger(CapituloCustomServiceImpl.class);

    private final CoolaboradorRepositoryCustom coolaboradorRepositoryCustom;

    private final CoolaboradorMapper coolaboradorMapper;

    private final SimpMessageSendingOperations messagingTemplate;

    private final NotificacionRepository notificacionRepository;

    private final NotificacionMapper notificacionMapper;

    private final CapituloMapper capituloMapper;

    private final CapituloRepository capituloRepository;

    public CoolaboradorCustomServiceImpl(CoolaboradorRepositoryCustom coolaboradorRepositoryCustom, CoolaboradorMapper coolaboradorMapper, SimpMessageSendingOperations messagingTemplate, NotificacionRepository notificacionRepository, NotificacionMapper notificacionMapper, CapituloMapper capituloMapper, CapituloRepository capituloRepository) {
        this.coolaboradorRepositoryCustom = coolaboradorRepositoryCustom;
        this.coolaboradorMapper = coolaboradorMapper;
        this.messagingTemplate = messagingTemplate;
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
        this.capituloMapper = capituloMapper;
        this.capituloRepository = capituloRepository;
    }


    /**
     * @author Maureen leon
     * Obtiene el colaborador siguiente por el id del capitulo
     * @param idCapituloSiguiente
     * @return CoolaboradorDTO
     * @version 1.0
     */
    @Override
    @Transactional(readOnly = true)
    public CoolaboradorDTO obtenerCoolaboradorPorIdCapitulo(Long idCapituloSiguiente) {
        Coolaborador coolaborador = coolaboradorRepositoryCustom.findByIdCapitulo(idCapituloSiguiente);
        return coolaboradorMapper.coolaboradorToCoolaboradorDTO(coolaborador);
    }

    /**
     * @author Maureen Leon
     * Notifica al siguiente usuario al terminar el capitulo en la publicacion compartida
     * @param capSiguiente
     * @param siguienteCoolaborador
     * @version 1.0
     */
    public void notificarSiguienteColaborador(CapituloDTO capSiguiente, CoolaboradorDTO siguienteCoolaborador) {

        NotificacionDTO notificacionDTO=new NotificacionDTO();
        notificacionDTO.setDescripcion("Eres el siguiente en colaborar en la publicación");
        notificacionDTO.setTipo("Compartida");
        notificacionDTO.setEstado(false);
        notificacionDTO.setIdUsuario(Math.toIntExact(siguienteCoolaborador.getIdUsuarioId()));
        notificacionDTO.setLink(capSiguiente.getId().toString());
        messagingTemplate.convertAndSend("/topic/" + Math.toIntExact(siguienteCoolaborador.getIdUsuarioId()), notificacionDTO);
        Notificacion notificacion = notificacionMapper.notificacionDTOToNotificacion(notificacionDTO);
        notificacion = notificacionRepository.save(notificacion);
    }

    /**
     * @author Maureen Leon
     * Notifica al siguiente usuario al terminar el capitulo en la publicacion compartida
     * @param capSiguiente
     * @param siguienteCoolaborador
     * @version 1.0
     */
    public void notificarColaboradorFinal(CapituloDTO capSiguiente, CoolaboradorDTO siguienteCoolaborador, Integer numCapFinal) {

        NotificacionDTO notificacionDTO=new NotificacionDTO();
        notificacionDTO.setDescripcion("La publicacion se ha concluido, porfavor agrega la conclusión");
        notificacionDTO.setTipo("Compartida");
        notificacionDTO.setEstado(false);
        notificacionDTO.setIdUsuario(Math.toIntExact(siguienteCoolaborador.getIdUsuarioId()));
        // Acá creamos un nuevo capitulo de la misma publicación pero es la conclusión.
        CapituloDTO capConclusionTemp = new CapituloDTO();
        capConclusionTemp.setNumeroCapitulo((numCapFinal+1));
        capConclusionTemp.setIdPublicacionCId(capSiguiente.getIdPublicacionCId());
        capConclusionTemp.setCapituloId(capSiguiente.getIdPublicacionCId());
        Capitulo capConclusionFinal = capituloMapper.capituloDTOToCapitulo(capConclusionTemp);
        capConclusionFinal = capituloRepository.save(capConclusionFinal);

        notificacionDTO.setLink(capConclusionFinal.getId().toString());
        messagingTemplate.convertAndSend("/topic/" + Math.toIntExact(siguienteCoolaborador.getIdUsuarioId()), notificacionDTO);
        Notificacion notificacion = notificacionMapper.notificacionDTOToNotificacion(notificacionDTO);
        notificacion = notificacionRepository.save(notificacion);


    }

}
