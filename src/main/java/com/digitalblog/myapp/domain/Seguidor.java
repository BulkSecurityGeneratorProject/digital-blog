package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Seguidor.
 */
@Entity
@Table(name = "seguidor")
public class Seguidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado_seguidor")
    private Boolean estadoSeguidor;

    @ManyToOne
    private Usuario idSeguidor;

    @ManyToOne
    private Usuario idSeguido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstadoSeguidor() {
        return estadoSeguidor;
    }

    public Seguidor estadoSeguidor(Boolean estadoSeguidor) {
        this.estadoSeguidor = estadoSeguidor;
        return this;
    }

    public void setEstadoSeguidor(Boolean estadoSeguidor) {
        this.estadoSeguidor = estadoSeguidor;
    }

    public Usuario getIdSeguidor() {
        return idSeguidor;
    }

    public Seguidor idSeguidor(Usuario usuario) {
        this.idSeguidor = usuario;
        return this;
    }

    public void setIdSeguidor(Usuario usuario) {
        this.idSeguidor = usuario;
    }

    public Usuario getIdSeguido() {
        return idSeguido;
    }

    public Seguidor idSeguido(Usuario usuario) {
        this.idSeguido = usuario;
        return this;
    }

    public void setIdSeguido(Usuario usuario) {
        this.idSeguido = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seguidor seguidor = (Seguidor) o;
        if (seguidor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, seguidor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Seguidor{" +
            "id=" + id +
            ", estadoSeguidor='" + estadoSeguidor + "'" +
            '}';
    }
}
