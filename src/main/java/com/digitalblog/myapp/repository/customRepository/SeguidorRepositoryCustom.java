package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Seguidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


public interface SeguidorRepositoryCustom extends JpaRepository<Seguidor,Long> {

    @Query("Select s from Seguidor s where s.idSeguido.id = :id")
    Collection<Seguidor> findAllByUserId(@Param("id") Long id);

    @Query("Select s from Seguidor s where s.idSeguidor.id = :id")
    Collection<Seguidor> findAllSeguidosByUserId(@Param("id") Long id);

    @Query("Select s from Seguidor s where s.idSeguidor.id = :seguidor and s.idSeguido.id = :seguido")
    Seguidor findOne(@Param("seguido") Long seguido,@Param("seguidor") Long seguidor);
}
