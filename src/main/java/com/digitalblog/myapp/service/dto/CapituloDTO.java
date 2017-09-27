package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Capitulo entity.
 */
public class CapituloDTO implements Serializable {

    private Long id;

    private Integer numeroCapitulo;

    private Long capituloId;

    private Long idPublicacionCId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroCapitulo() {
        return numeroCapitulo;
    }

    public void setNumeroCapitulo(Integer numeroCapitulo) {
        this.numeroCapitulo = numeroCapitulo;
    }

    public Long getCapituloId() {
        return capituloId;
    }

    public void setCapituloId(Long publicacionId) {
        this.capituloId = publicacionId;
    }

    public Long getIdPublicacionCId() {
        return idPublicacionCId;
    }

    public void setIdPublicacionCId(Long publicacionId) {
        this.idPublicacionCId = publicacionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CapituloDTO capituloDTO = (CapituloDTO) o;
        if(capituloDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), capituloDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CapituloDTO{" +
            "id=" + getId() +
            ", numeroCapitulo='" + getNumeroCapitulo() + "'" +
            "}";
    }
}
