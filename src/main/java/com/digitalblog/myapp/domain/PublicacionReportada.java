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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PublicacionReportada publicacionReportada = (PublicacionReportada) o;
        if (publicacionReportada.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, publicacionReportada.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PublicacionReportada{" +
            "id=" + id +
            ", cantidadReportes='" + cantidadReportes + "'" +
            ", idPublicacion='" + idPublicacion + "'" +
            '}';
    }
}
