package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Pagina entity.
 */
public class PaginaDTO implements Serializable {

    private Long id;

    private String contenido;

    private Integer numeroPagina;

    private Long capituloId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public Integer getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(Integer numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public Long getCapituloId() {
        return capituloId;
    }

    public void setCapituloId(Long capituloId) {
        this.capituloId = capituloId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaginaDTO paginaDTO = (PaginaDTO) o;

        if ( ! Objects.equals(id, paginaDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PaginaDTO{" +
            "id=" + id +
            ", contenido='" + contenido + "'" +
            ", numeroPagina='" + numeroPagina + "'" +
            '}';
    }
}
