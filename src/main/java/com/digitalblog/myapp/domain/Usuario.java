package com.digitalblog.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "primer_apelldio")
    private String primerApelldio;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "correo")
    private String correo;

    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "fotoperfil")
    private byte[] fotoperfil;

    @Column(name = "fotoperfil_content_type")
    private String fotoperfilContentType;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "id_jhi_user")
    private Long idJHIUser;

    @Column(name = "fecha_nacimiento")
    private ZonedDateTime fechaNacimiento;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<Publicacion> usuarios = new HashSet<>();

    @ManyToOne
    private Rol rol;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Usuario nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApelldio() {
        return primerApelldio;
    }

    public Usuario primerApelldio(String primerApelldio) {
        this.primerApelldio = primerApelldio;
        return this;
    }

    public void setPrimerApelldio(String primerApelldio) {
        this.primerApelldio = primerApelldio;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public Usuario segundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public Usuario edad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public Usuario correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Usuario descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFotoperfil() {
        return fotoperfil;
    }

    public Usuario fotoperfil(byte[] fotoperfil) {
        this.fotoperfil = fotoperfil;
        return this;
    }

    public void setFotoperfil(byte[] fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public String getFotoperfilContentType() {
        return fotoperfilContentType;
    }

    public Usuario fotoperfilContentType(String fotoperfilContentType) {
        this.fotoperfilContentType = fotoperfilContentType;
        return this;
    }

    public void setFotoperfilContentType(String fotoperfilContentType) {
        this.fotoperfilContentType = fotoperfilContentType;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Usuario estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Long getIdJHIUser() {
        return idJHIUser;
    }

    public Usuario idJHIUser(Long idJHIUser) {
        this.idJHIUser = idJHIUser;
        return this;
    }

    public void setIdJHIUser(Long idJHIUser) {
        this.idJHIUser = idJHIUser;
    }

    public ZonedDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Usuario fechaNacimiento(ZonedDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(ZonedDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Set<Publicacion> getUsuarios() {
        return usuarios;
    }

    public Usuario usuarios(Set<Publicacion> publicacions) {
        this.usuarios = publicacions;
        return this;
    }

    public Usuario addUsuario(Publicacion publicacion) {
        this.usuarios.add(publicacion);
        publicacion.setUsuario(this);
        return this;
    }

    public Usuario removeUsuario(Publicacion publicacion) {
        this.usuarios.remove(publicacion);
        publicacion.setUsuario(null);
        return this;
    }

    public void setUsuarios(Set<Publicacion> publicacions) {
        this.usuarios = publicacions;
    }

    public Rol getRol() {
        return rol;
    }

    public Usuario rol(Rol rol) {
        this.rol = rol;
        return this;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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
        Usuario usuario = (Usuario) o;
        if (usuario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", primerApelldio='" + getPrimerApelldio() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", edad='" + getEdad() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fotoperfil='" + getFotoperfil() + "'" +
            ", fotoperfilContentType='" + fotoperfilContentType + "'" +
            ", estado='" + isEstado() + "'" +
            ", idJHIUser='" + getIdJHIUser() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            "}";
    }
}
