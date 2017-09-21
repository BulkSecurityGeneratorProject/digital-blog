package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by jgm16 on 09/03/2017.
 */
public interface TemaRepositoryCustom extends JpaRepository<Tema,Long> {

    /**
     * obtiene el tema por el nombre
     * @param nombre que se le envia
     * @return entity tema
     */
    @Query("Select t from Tema t where t.nombre = :nombre")
    Tema findTemaByName(@Param("nombre") String nombre);
}
