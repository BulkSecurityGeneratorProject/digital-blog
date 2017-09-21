package com.digitalblog.myapp.service.impl.customServiceImpl;

import com.digitalblog.myapp.domain.Notificacion;
import com.digitalblog.myapp.repository.NotificacionRepository;
import com.digitalblog.myapp.repository.customRepository.NotificacionRepositoryCustom;
import com.digitalblog.myapp.service.customService.NotificacionCustomService;
import com.digitalblog.myapp.service.dto.NotificacionDTO;
import com.digitalblog.myapp.service.mapper.NotificacionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificacionCustomServiceImpl implements NotificacionCustomService {


    private final NotificacionRepositoryCustom notificacionRepository;

    private final NotificacionRepository notificacionRepositoryNo;

    private final NotificacionMapper notificacionMapper;

    public NotificacionCustomServiceImpl(NotificacionRepositoryCustom notificacionRepository, NotificacionRepository notificacionRepositoryNo, NotificacionMapper notificacionMapper) {
        this.notificacionRepository = notificacionRepository;
        this.notificacionRepositoryNo = notificacionRepositoryNo;
        this.notificacionMapper = notificacionMapper;
    }

    /**
     * @author Maureen Leon
     * findAllByUser obtiene las notificaciones de un usuario
     * @param id
     * @return List<NotificacionDTO>
     * @version 1.0
     */
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> findAllByUser(Integer id) {
        List<NotificacionDTO> result = notificacionRepository.findAllByUser(id,"Seguidor").stream()
            .map(notificacionMapper::notificacionToNotificacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * @author Maureen Leon
     * notificacionPublicaciones obtiene las notificaciones de tipo publicacion
     * @param id
     * @return List<NotificacionDTO>
     * @version 1.0
     */
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> notificacionPublicaciones(Integer id) {
        List<NotificacionDTO> result = notificacionRepository.findAllByUser(id,"Publicacion").stream()
            .map(notificacionMapper::notificacionToNotificacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * @author Maureen Leon
     * Obtiene las notificaciones
     * @param id
     * @param opc
     * @return List<NotificacionDTO>
     * @version 1.0
     */
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> getNotificaciones(Integer id, Integer opc) {

        String tipo="" ;

        if(opc == 1){
            tipo="Seguidor";
        }else if(opc == 2){
            tipo="Publicacion";
        }else if(opc == 3){
            tipo="Compartida";
        }

        List<NotificacionDTO> result = notificacionRepository.findAllByUser(id,tipo).stream()
            .map(notificacionMapper::notificacionToNotificacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * @author Maureen Leon
     * Cambia estado notificacion
     * @param id
     * @return NotificacionDTO
     * @version 1.0
     */
    @Override
    @Transactional
    public NotificacionDTO cambiarEstado(Long id) {
        Notificacion notificacion = notificacionRepositoryNo.findOne(id);
        notificacion.setEstado(true);
        notificacion = notificacionRepositoryNo.save(notificacion);
        return notificacionMapper.notificacionToNotificacionDTO(notificacion);
    }
    @Override
    @Transactional

    /**
     *  @author Christopher Sullivan
     *  @param id
     * @return Integer
     * @version 1.0
     */
    public Integer obtenerNotificcionesNoLeidas (Long id){
        List<Notificacion> result= notificacionRepository.obtenerNotificcionesNoLeidas(id);
        return result.size();
    }

    public List<NotificacionDTO> cambiarNotiLikeEstado (Long id){
        String ike = "Nuevo like a su publicación";

        List<Notificacion> result =notificacionRepository.cambiarNotiLikeEstado(id,ike);
        for(int i=0;i<result.size();i++){
            result.get(i).setEstado(true);
            notificacionRepositoryNo.save(result.get(i));
        }
        return null;
    }
}
