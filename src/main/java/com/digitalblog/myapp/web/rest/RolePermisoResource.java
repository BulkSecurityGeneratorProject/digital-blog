package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.RolePermisoService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.RolePermisoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing RolePermiso.
 */
@RestController
@RequestMapping("/api")
public class RolePermisoResource {

    private final Logger log = LoggerFactory.getLogger(RolePermisoResource.class);

    private static final String ENTITY_NAME = "rolePermiso";
        
    private final RolePermisoService rolePermisoService;

    public RolePermisoResource(RolePermisoService rolePermisoService) {
        this.rolePermisoService = rolePermisoService;
    }

    /**
     * POST  /role-permisos : Create a new rolePermiso.
     *
     * @param rolePermisoDTO the rolePermisoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rolePermisoDTO, or with status 400 (Bad Request) if the rolePermiso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/role-permisos")
    @Timed
    public ResponseEntity<RolePermisoDTO> createRolePermiso(@RequestBody RolePermisoDTO rolePermisoDTO) throws URISyntaxException {
        log.debug("REST request to save RolePermiso : {}", rolePermisoDTO);
        if (rolePermisoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rolePermiso cannot already have an ID")).body(null);
        }
        RolePermisoDTO result = rolePermisoService.save(rolePermisoDTO);
        return ResponseEntity.created(new URI("/api/role-permisos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /role-permisos : Updates an existing rolePermiso.
     *
     * @param rolePermisoDTO the rolePermisoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rolePermisoDTO,
     * or with status 400 (Bad Request) if the rolePermisoDTO is not valid,
     * or with status 500 (Internal Server Error) if the rolePermisoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/role-permisos")
    @Timed
    public ResponseEntity<RolePermisoDTO> updateRolePermiso(@RequestBody RolePermisoDTO rolePermisoDTO) throws URISyntaxException {
        log.debug("REST request to update RolePermiso : {}", rolePermisoDTO);
        if (rolePermisoDTO.getId() == null) {
            return createRolePermiso(rolePermisoDTO);
        }
        RolePermisoDTO result = rolePermisoService.save(rolePermisoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rolePermisoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /role-permisos : get all the rolePermisos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rolePermisos in body
     */
    @GetMapping("/role-permisos")
    @Timed
    public List<RolePermisoDTO> getAllRolePermisos() {
        log.debug("REST request to get all RolePermisos");
        return rolePermisoService.findAll();
    }

    /**
     * GET  /role-permisos/:id : get the "id" rolePermiso.
     *
     * @param id the id of the rolePermisoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rolePermisoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/role-permisos/{id}")
    @Timed
    public ResponseEntity<RolePermisoDTO> getRolePermiso(@PathVariable Long id) {
        log.debug("REST request to get RolePermiso : {}", id);
        RolePermisoDTO rolePermisoDTO = rolePermisoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rolePermisoDTO));
    }

    /**
     * DELETE  /role-permisos/:id : delete the "id" rolePermiso.
     *
     * @param id the id of the rolePermisoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/role-permisos/{id}")
    @Timed
    public ResponseEntity<Void> deleteRolePermiso(@PathVariable Long id) {
        log.debug("REST request to delete RolePermiso : {}", id);
        rolePermisoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
