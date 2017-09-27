package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Publicacion entity.
 */
public class PublicacionDTO implements Serializable {

    private Long id;

    private String urlImagen;

    private String descripcion;

    private String contenido;

    private Boolean tipo;

    private String titulo;

    private Integer estado;

    private Integer cantidadIteraciones;

    @Lob
    private byte[] fotopublicacion;
    private String fotopublicacionContentType;

    private Long usuarioId;

    private Long categoriaId;

    private Long temaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Boolean isTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getCantidadIteraciones() {
        return cantidadIteraciones;
    }

    public void setCantidadIteraciones(Integer cantidadIteraciones) {
        this.cantidadIteraciones = cantidadIteraciones;
    }

    public byte[] getFotopublicacion() {
        return fotopublicacion;
    }

    public void setFotopublicacion(byte[] fotopublicacion) {
        this.fotopublicacion = fotopublicacion;
    }

    public String getFotopublicacionContentType() {
        return fotopublicacionContentType;
    }

    public void setFotopublicacionContentType(String fotopublicacionContentType) {
        this.fotopublicacionContentType = fotopublicacionContentType;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getTemaId() {
        return temaId;
    }

    public void setTemaId(Long temaId) {
        this.temaId = temaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PublicacionDTO publicacionDTO = (PublicacionDTO) o;
        if(publicacionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publicacionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PublicacionDTO{" +
            "id=" + getId() +
            ", urlImagen='" + getUrlImagen() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", tipo='" + isTipo() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", estado='" + getEstado() + "'" +
            ", cantidadIteraciones='" + getCantidadIteraciones() + "'" +
            ", fotopublicacion='" + getFotopublicacion() + "'" +
            "}";
    }
}
