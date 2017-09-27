package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Respuesta entity.
 */
public class RespuestaDTO implements Serializable {

    private Long id;

    private String contenido;

    private Long idComentarioRId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getIdComentarioRId() {
        return idComentarioRId;
    }

    public void setIdComentarioRId(Long comentarioId) {
        this.idComentarioRId = comentarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RespuestaDTO respuestaDTO = (RespuestaDTO) o;

        if ( ! Objects.equals(id, respuestaDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RespuestaDTO{" +
            "id=" + id +
            ", contenido='" + contenido + "'" +
            '}';
    }
}
