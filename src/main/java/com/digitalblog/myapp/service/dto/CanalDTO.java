package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Canal entity.
 */
public class CanalDTO implements Serializable {

    private Long id;

    private Integer idUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CanalDTO canalDTO = (CanalDTO) o;

        if ( ! Objects.equals(id, canalDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CanalDTO{" +
            "id=" + id +
            ", idUsuario='" + idUsuario + "'" +
            '}';
    }
}
