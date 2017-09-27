package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RolePermiso entity.
 */
public class RolePermisoDTO implements Serializable {

    private Long id;

    private Long idPermisoId;

    private Long idRolId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPermisoId() {
        return idPermisoId;
    }

    public void setIdPermisoId(Long permisoId) {
        this.idPermisoId = permisoId;
    }

    public Long getIdRolId() {
        return idRolId;
    }

    public void setIdRolId(Long rolId) {
        this.idRolId = rolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RolePermisoDTO rolePermisoDTO = (RolePermisoDTO) o;
        if(rolePermisoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rolePermisoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RolePermisoDTO{" +
            "id=" + getId() +
            "}";
    }
}
