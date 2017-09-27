package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LikeT.
 */
@Entity
@Table(name = "like_t")
public class LikeT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne
    private Usuario idUsuarioL;

    @ManyToOne
    private Publicacion idLikeP;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public LikeT cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Usuario getIdUsuarioL() {
        return idUsuarioL;
    }

    public LikeT idUsuarioL(Usuario usuario) {
        this.idUsuarioL = usuario;
        return this;
    }

    public void setIdUsuarioL(Usuario usuario) {
        this.idUsuarioL = usuario;
    }

    public Publicacion getIdLikeP() {
        return idLikeP;
    }

    public LikeT idLikeP(Publicacion publicacion) {
        this.idLikeP = publicacion;
        return this;
    }

    public void setIdLikeP(Publicacion publicacion) {
        this.idLikeP = publicacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LikeT likeT = (LikeT) o;
        if (likeT.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, likeT.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LikeT{" +
            "id=" + id +
            ", cantidad='" + cantidad + "'" +
            '}';
    }
}
