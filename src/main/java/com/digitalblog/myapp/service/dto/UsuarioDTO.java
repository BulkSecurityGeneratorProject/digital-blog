package com.digitalblog.myapp.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Usuario entity.
 */
public class UsuarioDTO implements Serializable {

    private Long id;

    private String nombre;

    private String primerApelldio;

    private String segundoApellido;

    private Integer edad;

    private String correo;

    private String descripcion;

    @Lob
    private byte[] fotoperfil;
    private String fotoperfilContentType;

    private Boolean estado;

    private Long idJHIUser;

    private ZonedDateTime fechaNacimiento;

    private Long rolId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPrimerApelldio() {
        return primerApelldio;
    }

    public void setPrimerApelldio(String primerApelldio) {
        this.primerApelldio = primerApelldio;
    }
    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }
    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public byte[] getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(byte[] fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public String getFotoperfilContentType() {
        return fotoperfilContentType;
    }

    public void setFotoperfilContentType(String fotoperfilContentType) {
        this.fotoperfilContentType = fotoperfilContentType;
    }
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    public Long getIdJHIUser() {
        return idJHIUser;
    }

    public void setIdJHIUser(Long idJHIUser) {
        this.idJHIUser = idJHIUser;
    }
    public ZonedDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(ZonedDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsuarioDTO usuarioDTO = (UsuarioDTO) o;

        if ( ! Objects.equals(id, usuarioDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", primerApelldio='" + primerApelldio + "'" +
            ", segundoApellido='" + segundoApellido + "'" +
            ", edad='" + edad + "'" +
            ", correo='" + correo + "'" +
            ", descripcion='" + descripcion + "'" +
            ", fotoperfil='" + fotoperfil + "'" +
            ", estado='" + estado + "'" +
            ", idJHIUser='" + idJHIUser + "'" +
            ", fechaNacimiento='" + fechaNacimiento + "'" +
            '}';
    }
}
