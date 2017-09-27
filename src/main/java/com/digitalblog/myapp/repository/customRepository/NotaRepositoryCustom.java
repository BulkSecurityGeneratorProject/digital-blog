package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by suhu2 on 4/10/2017.
 */
public interface NotaRepositoryCustom extends JpaRepository<Nota,Long> {
    @Query(value = "select * from nota where pagina_nota_id = :idPagina",nativeQuery = true)
    List<Nota> findByIdPagina(@Param("idPagina")Long idPagina);
}
