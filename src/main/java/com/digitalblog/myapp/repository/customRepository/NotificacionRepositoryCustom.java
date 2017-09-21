package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Notificacion;
import com.digitalblog.myapp.service.dto.NotificacionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;


public interface NotificacionRepositoryCustom extends JpaRepository<Notificacion,Long> {

    @Query("Select n from Notificacion n where n.idUsuario = :id and n.tipo = :tipo")
    Collection<Notificacion> findAllByUser(@Param("id")Integer id,@Param("tipo")String tipo);

    @Query(value = "select * from notificacion where id_usuario=:id and estado = false",nativeQuery = true)
    List<Notificacion> obtenerNotificcionesNoLeidas(@Param ("id") Long id);


    @Query(value ="select * from notificacion where descripcion LIKE :ike and id_usuario=:id",nativeQuery = true)
    List<Notificacion> cambiarNotiLikeEstado(@Param ("id") Long id, @Param ("ike") String ike);
}
