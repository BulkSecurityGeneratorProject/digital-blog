package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Comentario.
 */
@Entity
@Table(name = "comentario")
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contenido")
    private String contenido;

    @OneToOne
    @JoinColumn(unique = true)
    private Respuesta idComentarioRespuesta;

    @ManyToOne
    private Usuario idComentarioU;

    @ManyToOne
    private Publicacion idComentarioP;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public Comentario contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Respuesta getIdComentarioRespuesta() {
        return idComentarioRespuesta;
    }

    public Comentario idComentarioRespuesta(Respuesta respuesta) {
        this.idComentarioRespuesta = respuesta;
        return this;
    }

    public void setIdComentarioRespuesta(Respuesta respuesta) {
        this.idComentarioRespuesta = respuesta;
    }

    public Usuario getIdComentarioU() {
        return idComentarioU;
    }

    public Comentario idComentarioU(Usuario usuario) {
        this.idComentarioU = usuario;
        return this;
    }

    public void setIdComentarioU(Usuario usuario) {
        this.idComentarioU = usuario;
    }

    public Publicacion getIdComentarioP() {
        return idComentarioP;
    }

    public Comentario idComentarioP(Publicacion publicacion) {
        this.idComentarioP = publicacion;
        return this;
    }

    public void setIdComentarioP(Publicacion publicacion) {
        this.idComentarioP = publicacion;
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
        Comentario comentario = (Comentario) o;
        if (comentario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comentario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comentario{" +
            "id=" + getId() +
            ", contenido='" + getContenido() + "'" +
            "}";
    }
}
