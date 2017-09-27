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
        if(paginaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paginaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaginaDTO{" +
            "id=" + getId() +
            ", contenido='" + getContenido() + "'" +
            ", numeroPagina='" + getNumeroPagina() + "'" +
            "}";
    }
}
