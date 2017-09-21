package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Respuesta.
 */
@Entity
@Table(name = "respuesta")
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contenido")
    private String contenido;

    @ManyToOne
    private Comentario idComentarioR;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public Respuesta contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Comentario getIdComentarioR() {
        return idComentarioR;
    }

    public Respuesta idComentarioR(Comentario comentario) {
        this.idComentarioR = comentario;
        return this;
    }

    public void setIdComentarioR(Comentario comentario) {
        this.idComentarioR = comentario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Respuesta respuesta = (Respuesta) o;
        if (respuesta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, respuesta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Respuesta{" +
            "id=" + id +
            ", contenido='" + contenido + "'" +
            '}';
    }
}
