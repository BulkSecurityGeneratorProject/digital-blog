package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Comentario entity.
 */
public class ComentarioDTO implements Serializable {

    private Long id;

    private String contenido;

    private Long idComentarioRespuestaId;

    private Long idComentarioUId;

    private Long idComentarioPId;

    private String respuesta;

    private String fotoComentador;

    public String getFotoComentador() {
        return fotoComentador;
    }

    public void setFotoComentador(String fotoComentador) {
        this.fotoComentador = fotoComentador;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

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

    public Long getIdComentarioRespuestaId() {
        return idComentarioRespuestaId;
    }

    public void setIdComentarioRespuestaId(Long respuestaId) {
        this.idComentarioRespuestaId = respuestaId;
    }

    public Long getIdComentarioUId() {
        return idComentarioUId;
    }

    public void setIdComentarioUId(Long usuarioId) {
        this.idComentarioUId = usuarioId;
    }

    public Long getIdComentarioPId() {
        return idComentarioPId;
    }

    public void setIdComentarioPId(Long publicacionId) {
        this.idComentarioPId = publicacionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComentarioDTO comentarioDTO = (ComentarioDTO) o;

        if ( ! Objects.equals(id, comentarioDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ComentarioDTO{" +
            "id=" + id +
            ", contenido='" + contenido + "'" +
            '}';
    }
}
