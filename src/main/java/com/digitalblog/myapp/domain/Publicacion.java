package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Publicacion.
 */
@Entity
@Table(name = "publicacion")
public class Publicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_imagen")
    private String urlImagen;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "tipo")
    private Boolean tipo;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "cantidad_iteraciones")
    private Integer cantidadIteraciones;

    @Lob
    @Column(name = "fotopublicacion")
    private byte[] fotopublicacion;

    @Column(name = "fotopublicacion_content_type")
    private String fotopublicacionContentType;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Tema tema;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public Publicacion urlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
        return this;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Publicacion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public Publicacion contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Boolean isTipo() {
        return tipo;
    }

    public Publicacion tipo(Boolean tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public Publicacion titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getEstado() {
        return estado;
    }

    public Publicacion estado(Integer estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getCantidadIteraciones() {
        return cantidadIteraciones;
    }

    public Publicacion cantidadIteraciones(Integer cantidadIteraciones) {
        this.cantidadIteraciones = cantidadIteraciones;
        return this;
    }

    public void setCantidadIteraciones(Integer cantidadIteraciones) {
        this.cantidadIteraciones = cantidadIteraciones;
    }

    public byte[] getFotopublicacion() {
        return fotopublicacion;
    }

    public Publicacion fotopublicacion(byte[] fotopublicacion) {
        this.fotopublicacion = fotopublicacion;
        return this;
    }

    public void setFotopublicacion(byte[] fotopublicacion) {
        this.fotopublicacion = fotopublicacion;
    }

    public String getFotopublicacionContentType() {
        return fotopublicacionContentType;
    }

    public Publicacion fotopublicacionContentType(String fotopublicacionContentType) {
        this.fotopublicacionContentType = fotopublicacionContentType;
        return this;
    }

    public void setFotopublicacionContentType(String fotopublicacionContentType) {
        this.fotopublicacionContentType = fotopublicacionContentType;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Publicacion usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Publicacion categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Tema getTema() {
        return tema;
    }

    public Publicacion tema(Tema tema) {
        this.tema = tema;
        return this;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
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
        Publicacion publicacion = (Publicacion) o;
        if (publicacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publicacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Publicacion{" +
            "id=" + getId() +
            ", urlImagen='" + getUrlImagen() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", tipo='" + isTipo() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", estado='" + getEstado() + "'" +
            ", cantidadIteraciones='" + getCantidadIteraciones() + "'" +
            ", fotopublicacion='" + getFotopublicacion() + "'" +
            ", fotopublicacionContentType='" + fotopublicacionContentType + "'" +
            "}";
    }
}
