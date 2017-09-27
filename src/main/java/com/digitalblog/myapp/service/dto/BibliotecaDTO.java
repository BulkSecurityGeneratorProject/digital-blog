package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Biblioteca entity.
 */
public class BibliotecaDTO implements Serializable {

    private Long id;

    private Integer idSeccion;

    private Long idJhiUser;

    private Long idUsuarioBId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }
    public Long getIdJhiUser() {
        return idJhiUser;
    }

    public void setIdJhiUser(Long idJhiUser) {
        this.idJhiUser = idJhiUser;
    }

    public Long getIdUsuarioBId() {
        return idUsuarioBId;
    }

    public void setIdUsuarioBId(Long usuarioId) {
        this.idUsuarioBId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BibliotecaDTO bibliotecaDTO = (BibliotecaDTO) o;

        if ( ! Objects.equals(id, bibliotecaDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BibliotecaDTO{" +
            "id=" + id +
            ", idSeccion='" + idSeccion + "'" +
            ", idJhiUser='" + idJhiUser + "'" +
            '}';
    }
}
