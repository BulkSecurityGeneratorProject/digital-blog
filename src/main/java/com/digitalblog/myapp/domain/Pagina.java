package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pagina.
 */
@Entity
@Table(name = "pagina")
public class Pagina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "numero_pagina")
    private Integer numeroPagina;

    @ManyToOne
    private Capitulo capitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public Pagina contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Integer getNumeroPagina() {
        return numeroPagina;
    }

    public Pagina numeroPagina(Integer numeroPagina) {
        this.numeroPagina = numeroPagina;
        return this;
    }

    public void setNumeroPagina(Integer numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public Capitulo getCapitulo() {
        return capitulo;
    }

    public Pagina capitulo(Capitulo capitulo) {
        this.capitulo = capitulo;
        return this;
    }

    public void setCapitulo(Capitulo capitulo) {
        this.capitulo = capitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pagina pagina = (Pagina) o;
        if (pagina.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pagina.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pagina{" +
            "id=" + id +
            ", contenido='" + contenido + "'" +
            ", numeroPagina='" + numeroPagina + "'" +
            '}';
    }
}
