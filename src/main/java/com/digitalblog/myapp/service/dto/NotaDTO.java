package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Nota entity.
 */
public class NotaDTO implements Serializable {

    private Long id;

    private String contenido;

    private Long paginaNotaId;

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

    public Long getPaginaNotaId() {
        return paginaNotaId;
    }

    public void setPaginaNotaId(Long paginaId) {
        this.paginaNotaId = paginaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotaDTO notaDTO = (NotaDTO) o;

        if ( ! Objects.equals(id, notaDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NotaDTO{" +
            "id=" + id +
            ", contenido='" + contenido + "'" +
            '}';
    }
}
