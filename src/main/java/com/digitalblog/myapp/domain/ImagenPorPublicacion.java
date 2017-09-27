package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ImagenPorPublicacion.
 */
@Entity
@Table(name = "imagen_por_publicacion")
public class ImagenPorPublicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_publicacion")
    private Integer idPublicacion;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPublicacion() {
        return idPublicacion;
    }

    public ImagenPorPublicacion idPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
        return this;
    }

    public void setIdPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public ImagenPorPublicacion imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public ImagenPorPublicacion imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
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
        ImagenPorPublicacion imagenPorPublicacion = (ImagenPorPublicacion) o;
        if (imagenPorPublicacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imagenPorPublicacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImagenPorPublicacion{" +
            "id=" + getId() +
            ", idPublicacion='" + getIdPublicacion() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + imagenContentType + "'" +
            "}";
    }
}
