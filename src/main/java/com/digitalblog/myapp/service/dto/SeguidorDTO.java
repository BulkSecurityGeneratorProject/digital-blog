package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Seguidor entity.
 */
public class SeguidorDTO implements Serializable {

    private Long id;

    private Boolean estadoSeguidor;

    private Long idSeguidorId;

    private Long idSeguidoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getEstadoSeguidor() {
        return estadoSeguidor;
    }

    public void setEstadoSeguidor(Boolean estadoSeguidor) {
        this.estadoSeguidor = estadoSeguidor;
    }

    public Long getIdSeguidorId() {
        return idSeguidorId;
    }

    public void setIdSeguidorId(Long usuarioId) {
        this.idSeguidorId = usuarioId;
    }

    public Long getIdSeguidoId() {
        return idSeguidoId;
    }

    public void setIdSeguidoId(Long usuarioId) {
        this.idSeguidoId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeguidorDTO seguidorDTO = (SeguidorDTO) o;

        if ( ! Objects.equals(id, seguidorDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SeguidorDTO{" +
            "id=" + id +
            ", estadoSeguidor='" + estadoSeguidor + "'" +
            '}';
    }
}
