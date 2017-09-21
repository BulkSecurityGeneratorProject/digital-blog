package com.digitalblog.myapp.repository.customRepository;

import com.digitalblog.myapp.domain.Pagina;
import com.digitalblog.myapp.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;


public interface PaginaRepositoryCustom extends JpaRepository<Usuario,Long> {

    @Query("Select p from Pagina p where p.capitulo.id = :id")
    ArrayList<Pagina> findByIdCapitulo(@Param("id") Long id);

    @Query("Select p from Pagina p where p.capitulo.id = :capituloId AND p.numeroPagina =:numpagina")
    Pagina buscarUltimaPaginaPorIdYCapituloId(@Param("numpagina") Integer numPagina,@Param("capituloId") Long capituloId);

}
