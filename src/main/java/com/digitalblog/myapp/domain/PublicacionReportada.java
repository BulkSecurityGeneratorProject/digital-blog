package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PublicacionReportada.
 */
@Entity
@Table(name = "publicacion_reportada")
public class PublicacionReportada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad_reportes")
    private Integer cantidadReportes;

    @Column(name = "id_publicacion")
    private Integer idPublicacion;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidadReportes() {
        return cantidadReportes;
    }

    public PublicacionReportada cantidadReportes(Integer cantidadReportes) {
        this.cantidadReportes = cantidadReportes;
        return this;
    }

    public void setCantidadReportes(Integer cantidadReportes) {
        this.cantidadReportes = cantidadReportes;
    }

    public Integer getIdPublicacion() {
        return idPublicacion;
    }

    public PublicacionReportada idPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
        return this;
    }

    public void setIdPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PublicacionReportada publicacionReportada = (PublicacionReportada) o;
        if (publicacionReportada.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publicacionReportada.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PublicacionReportada{" +
            "id=" + getId() +
            ", cantidadReportes='" + getCantidadReportes() + "'" +
            ", idPublicacion='" + getIdPublicacion() + "'" +
            "}";
    }
}
