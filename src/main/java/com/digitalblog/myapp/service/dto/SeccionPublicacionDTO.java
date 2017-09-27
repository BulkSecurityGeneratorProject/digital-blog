package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SeccionPublicacion entity.
 */
public class SeccionPublicacionDTO implements Serializable {

    private Long id;

    private Long idSeccionSPId;

    private Long idPublicacionSPId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSeccionSPId() {
        return idSeccionSPId;
    }

    public void setIdSeccionSPId(Long seccionId) {
        this.idSeccionSPId = seccionId;
    }

    public Long getIdPublicacionSPId() {
        return idPublicacionSPId;
    }

    public void setIdPublicacionSPId(Long publicacionId) {
        this.idPublicacionSPId = publicacionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeccionPublicacionDTO seccionPublicacionDTO = (SeccionPublicacionDTO) o;
        if(seccionPublicacionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seccionPublicacionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeccionPublicacionDTO{" +
            "id=" + getId() +
            "}";
    }
}
