package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Suscripciones entity.
 */
public class SuscripcionesDTO implements Serializable {

    private Long id;

    private Integer idCanal;

    private Integer idSiguiendo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(Integer idCanal) {
        this.idCanal = idCanal;
    }
    public Integer getIdSiguiendo() {
        return idSiguiendo;
    }

    public void setIdSiguiendo(Integer idSiguiendo) {
        this.idSiguiendo = idSiguiendo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SuscripcionesDTO suscripcionesDTO = (SuscripcionesDTO) o;

        if ( ! Objects.equals(id, suscripcionesDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SuscripcionesDTO{" +
            "id=" + id +
            ", idCanal='" + idCanal + "'" +
            ", idSiguiendo='" + idSiguiendo + "'" +
            '}';
    }
}
