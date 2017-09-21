package com.digitalblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.RolService;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import com.digitalblog.myapp.service.dto.RolDTO;
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
 * REST controller for managing Rol.
 */
@RestController
@RequestMapping("/api")
public class RolResource {

    private final Logger log = LoggerFactory.getLogger(RolResource.class);

    private static final String ENTITY_NAME = "rol";
        
    private final RolService rolService;

    public RolResource(RolService rolService) {
        this.rolService = rolService;
    }

    /**
     * POST  /rols : Create a new rol.
     *
     * @param rolDTO the rolDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rolDTO, or with status 400 (Bad Request) if the rol has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rols")
    @Timed
    public ResponseEntity<RolDTO> createRol(@RequestBody RolDTO rolDTO) throws URISyntaxException {
        log.debug("REST request to save Rol : {}", rolDTO);
        if (rolDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rol cannot already have an ID")).body(null);
        }
        RolDTO result = rolService.save(rolDTO);
        return ResponseEntity.created(new URI("/api/rols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rols : Updates an existing rol.
     *
     * @param rolDTO the rolDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rolDTO,
     * or with status 400 (Bad Request) if the rolDTO is not valid,
     * or with status 500 (Internal Server Error) if the rolDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rols")
    @Timed
    public ResponseEntity<RolDTO> updateRol(@RequestBody RolDTO rolDTO) throws URISyntaxException {
        log.debug("REST request to update Rol : {}", rolDTO);
        if (rolDTO.getId() == null) {
            return createRol(rolDTO);
        }
        RolDTO result = rolService.save(rolDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rolDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rols : get all the rols.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rols in body
     */
    @GetMapping("/rols")
    @Timed
    public List<RolDTO> getAllRols() {
        log.debug("REST request to get all Rols");
        return rolService.findAll();
    }

    /**
     * GET  /rols/:id : get the "id" rol.
     *
     * @param id the id of the rolDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rolDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rols/{id}")
    @Timed
    public ResponseEntity<RolDTO> getRol(@PathVariable Long id) {
        log.debug("REST request to get Rol : {}", id);
        RolDTO rolDTO = rolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rolDTO));
    }

    /**
     * DELETE  /rols/:id : delete the "id" rol.
     *
     * @param id the id of the rolDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rols/{id}")
    @Timed
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        log.debug("REST request to delete Rol : {}", id);
        rolService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
