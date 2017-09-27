package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

/**
 * Spring Data JPA repository for the Seccion entity.
 */
@SuppressWarnings("unused")
public interface SeccionRepositoryCustom extends JpaRepository<Seccion,Long> {

    /*
    *Se busca la seccion por medio de la biblioteca del usuario
     */
    @Query("Select s from Seccion s where s.biblioteca.id = :id")
    ArrayList<Seccion> findAllByIdBiblioteca(@Param("id") Long id);

    @Query(value = "Select * from Seccion where Seccion.nombre =:name AND Seccion.biblioteca_id =:id",nativeQuery = true)
    Seccion findOneByName(@Param("name") String name,@Param("id") Long id);

    /**
     * Busca la secccion de una biblioteca especifica
     * recibe el el id de la biblioteca y el nombre de la seccion
     */
    @Query("Select s from Seccion s where s.biblioteca.id = :idBiblioteca and s.nombre = :nombreSeccion  ")
    Seccion findSeccionByNameById(@Param("idBiblioteca")Long idBiblioteca,@Param("nombreSeccion") String nombreSeccion);
}
