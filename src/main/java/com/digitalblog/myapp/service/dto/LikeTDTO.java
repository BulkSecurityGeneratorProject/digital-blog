package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the LikeT entity.
 */
public class LikeTDTO implements Serializable {

    private Long id;

    private Integer cantidad;

    private Long idUsuarioLId;

    private Long idLikePId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getIdUsuarioLId() {
        return idUsuarioLId;
    }

    public void setIdUsuarioLId(Long usuarioId) {
        this.idUsuarioLId = usuarioId;
    }

    public Long getIdLikePId() {
        return idLikePId;
    }

    public void setIdLikePId(Long publicacionId) {
        this.idLikePId = publicacionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LikeTDTO likeTDTO = (LikeTDTO) o;

        if ( ! Objects.equals(id, likeTDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LikeTDTO{" +
            "id=" + id +
            ", cantidad='" + cantidad + "'" +
            '}';
    }
}
