package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ImagenPorPublicacion entity.
 */
public class ImagenPorPublicacionDTO implements Serializable {

    private Long id;

    private Integer idPublicacion;

    @Lob
    private byte[] imagen;
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

    public void setIdPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }
    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
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

        ImagenPorPublicacionDTO imagenPorPublicacionDTO = (ImagenPorPublicacionDTO) o;

        if ( ! Objects.equals(id, imagenPorPublicacionDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ImagenPorPublicacionDTO{" +
            "id=" + id +
            ", idPublicacion='" + idPublicacion + "'" +
            ", imagen='" + imagen + "'" +
            '}';
    }
}
