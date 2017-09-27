package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Coolaborador entity.
 */
public class CoolaboradorDTO implements Serializable {

    private Long id;

    private Long capituloCId;

    private Long publicacionId;

    private Long idUsuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCapituloCId() {
        return capituloCId;
    }

    public void setCapituloCId(Long capituloId) {
        this.capituloCId = capituloId;
    }

    public Long getPublicacionId() {
        return publicacionId;
    }

    public void setPublicacionId(Long publicacionId) {
        this.publicacionId = publicacionId;
    }

    public Long getIdUsuarioId() {
        return idUsuarioId;
    }

    public void setIdUsuarioId(Long usuarioId) {
        this.idUsuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoolaboradorDTO coolaboradorDTO = (CoolaboradorDTO) o;
        if(coolaboradorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coolaboradorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoolaboradorDTO{" +
            "id=" + getId() +
            "}";
    }
}
