package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PublicacionReportada entity.
 */
public class PublicacionReportadaDTO implements Serializable {

    private Long id;

    private Integer cantidadReportes;

    private Integer idPublicacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getCantidadReportes() {
        return cantidadReportes;
    }

    public void setCantidadReportes(Integer cantidadReportes) {
        this.cantidadReportes = cantidadReportes;
    }
    public Integer getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PublicacionReportadaDTO publicacionReportadaDTO = (PublicacionReportadaDTO) o;

        if ( ! Objects.equals(id, publicacionReportadaDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PublicacionReportadaDTO{" +
            "id=" + id +
            ", cantidadReportes='" + cantidadReportes + "'" +
            ", idPublicacion='" + idPublicacion + "'" +
            '}';
    }
}
