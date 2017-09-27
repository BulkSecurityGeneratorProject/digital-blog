package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by Maureen on 3/14/2017.
 */
public interface PublicacionRepositoryCustom extends JpaRepository<Publicacion,Long> {

    @Query("Select p from Publicacion p where p.usuario.id = :id and p.estado = :estado")
    Collection<Publicacion> findAll(@Param("id")Long id,@Param("estado")Integer estado);

    //@Query("Select p from Publicacion p where p.usuario.id = :idUsuario and p.")
  //  @Query(value = "SELECT * FROM publicacion WHERE usuario_id = :idUsuario and tema_id = :tema", nativeQuery = true)
    @Query(value = "SELECT * FROM publicacion\n" +
        "    join seccion_publicacion on (seccion_publicacion.id_publicacionsp_id = publicacion.id)\n" +
        "    where seccion_publicacion.id_seccionsp_id =:seccion", nativeQuery = true)
    List<Publicacion> findByusuarioIdandtemaId(@Param("seccion")Long seccion);

    @Query(value = "SELECT * FROM PUBLICACION WHERE ESTADO = 2",nativeQuery = true)
    List<Publicacion> findByPublicacionPublicada();

    @Query(value = "SELECT * FROM Publicacion \n" +
        "JOIN Usuario ON (Usuario.id = Publicacion.usuario_id)\n" +
        "JOIN Categoria ON (categoria.id = Publicacion.categoria_id)\n" +
        "JOIN Tema ON (Tema.id = Publicacion.tema_id)\n" +
        "WHERE Publicacion.estado = 2 AND (Usuario.nombre LIKE :param OR Categoria.nombre LIKE :param OR Tema.nombre LIKE :param OR Publicacion.titulo LIKE :param)",nativeQuery = true)
    List<Publicacion> findByPublicacionPorAutorTemaCategoria(@Param("param") String param);

    @Query(value = "SELECT * FROM PUBLICACION \n" +
        "JOIN USUARIO ON (USUARIO.ID = PUBLICACION.usuario_id)\n" +
        "JOIN categoria ON (categoria.id = PUBLICACION.categoria_id)\n" +
        "JOIN tema ON (tema.id = PUBLICACION.tema_id)\n" +
        "WHERE PUBLICACION.ESTADO = 2 AND categoria.nombre  LIKE :categoriaNombre AND tema.nombre LIKE :temaNombre",nativeQuery = true)
    List<Publicacion> findByPublicacionPorCategoriaYtema(@Param("categoriaNombre") String categoriaNombre,@Param("temaNombre") String temaNombre);

    @Query(value = "SELECT usuario_id FROM publicacion where id = :idPublicacion",nativeQuery = true)
    Long findIdUsuarioByIdPublicacion(@Param("idPublicacion")Long idPublicacion);
}
