package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Notificacion entity.
 */
public class NotificacionDTO implements Serializable {

    private Long id;

    private String descripcion;

    private String tipo;

    private Integer idUsuario;

    private String link;

    private Boolean estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificacionDTO notificacionDTO = (NotificacionDTO) o;

        if ( ! Objects.equals(id, notificacionDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NotificacionDTO{" +
            "id=" + id +
            ", descripcion='" + descripcion + "'" +
            ", tipo='" + tipo + "'" +
            ", idUsuario='" + idUsuario + "'" +
            ", link='" + link + "'" +
            ", estado='" + estado + "'" +
            '}';
    }
}
