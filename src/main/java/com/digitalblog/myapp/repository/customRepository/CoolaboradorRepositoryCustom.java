package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Coolaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by jose_ on 13/4/2017.
 */
public interface CoolaboradorRepositoryCustom extends JpaRepository<Coolaborador,Long> {

    @Query("Select co from Coolaborador co where co.capituloC.id = :id")
    Coolaborador findByIdCapitulo(@Param("id") Long id);

}
