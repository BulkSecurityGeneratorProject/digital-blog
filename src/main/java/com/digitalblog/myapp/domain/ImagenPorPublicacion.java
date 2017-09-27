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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImagenPorPublicacion imagenPorPublicacion = (ImagenPorPublicacion) o;
        if (imagenPorPublicacion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, imagenPorPublicacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ImagenPorPublicacion{" +
            "id=" + id +
            ", idPublicacion='" + idPublicacion + "'" +
            ", imagen='" + imagen + "'" +
            ", imagenContentType='" + imagenContentType + "'" +
            '}';
    }
}
