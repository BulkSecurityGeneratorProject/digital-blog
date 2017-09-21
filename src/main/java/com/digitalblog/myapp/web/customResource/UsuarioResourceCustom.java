package com.digitalblog.myapp.web.customResource;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.UsuarioService;
import com.digitalblog.myapp.service.customService.SeguidorCustomService;
import com.digitalblog.myapp.service.customService.UsuarioCustomService;
import com.digitalblog.myapp.service.dto.SeguidorDTO;
import com.digitalblog.myapp.service.dto.UsuarioDTO;
import com.digitalblog.myapp.web.rest.UsuarioResource;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/apiCustom")
public class UsuarioResourceCustom {

    private final Logger log = LoggerFactory.getLogger(UsuarioResourceCustom.class);
    private static final String ENTITY_NAME = "usuario";
    private final UsuarioCustomService usuarioCustomService;
    private final UsuarioService usuarioService;
    private final UsuarioResource usuarioResource;
    private final SeguidorCustomService seguidorCustomService;

    private Cloudinary cloudinary ;


    public UsuarioResourceCustom(UsuarioCustomService usuarioCustomService, UsuarioService usuarioService, UsuarioResource usuarioResource,SeguidorCustomService seguidorCustomService) {
        this.usuarioCustomService = usuarioCustomService;
        this.usuarioService=usuarioService;
        this.usuarioResource = usuarioResource;
        this.seguidorCustomService = seguidorCustomService;
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dblelmjha",
            "api_key", "592312911323426",
            "api_secret", "tPoLiNw8mpi9Qu5xkzEGx8vRKiQ"));
    }


    /**
     * GET  /usuarios/{id} :
     * @author Mauren Leon
     * Obtiene usuario normal por el id del usuario del jhi user
     * @param id the Long to obtener el usuario con el id del JHIUser
     * @return the ResponseEntity UsuarioDTO
     * @version 1.0
     */
    @GetMapping("/usuarios/{id}")
    @Timed
    public ResponseEntity<UsuarioDTO> obtenerUsuarioByJhiUserId(@PathVariable Long id) {
        log.debug("REST request to get Usuario : {}", id);
        UsuarioDTO usuarioDTO = usuarioCustomService.findByJhiUserId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(usuarioDTO));
    }



    /**
     * PUT  /usuariosUpdate : actualiza un usuario existente.
     * @author Maureen Leon
     * Actualiza el usuario normal
     * @param usuarioDTO the usuarioDTO recibe el usuario valida si va a hacer un cambio de imagen
     * @return the ResponseEntity with status 200 (OK) and with body the updated usuarioDTO,
     * or with status 400 (Bad Request) if the usuarioDTO is not valid,
     * or with status 500 (Internal Server Error) if the usuarioDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws  IOException
     * @version 1.0
     */
    @PutMapping("/usuariosUpdate")
    @Timed
    public ResponseEntity<UsuarioDTO> updateUsuario(@RequestBody UsuarioDTO usuarioDTO) throws URISyntaxException, IOException {
        if(usuarioDTO.getFotoperfil() != null) {
           usuarioDTO = cargarImagen(usuarioDTO);
        }
        if (usuarioDTO.getId() == null) {
            return usuarioResource.createUsuario(usuarioDTO);
        }
        UsuarioDTO result = usuarioService.save(usuarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, usuarioDTO.getId().toString()))
            .body(result);
    }


    /**
     * @author Maureen Leon
     * cargarImagen : Carga imagen en cloudinary.
     * @param usuarioDTO the usuarioDTO
     * @return UsuarioDTO con el url que retorna cloudinary seteado
     * @throws IOException
     * @version 1.0
     */
    private UsuarioDTO cargarImagen(UsuarioDTO usuarioDTO) throws IOException {

            byte[] bytes = usuarioDTO.getFotoperfil();
            Map uploadResult = cloudinary.uploader().upload(bytes, ObjectUtils.emptyMap());
            Object url = uploadResult.get("url");
            usuarioDTO.setFotoperfilContentType((String) url);
            usuarioDTO.setFotoperfil(null);
            return usuarioDTO;
    }


    /**
     * GET  /usuariosSeguidores/{id} :
     * @author Maureen Leon
     * @param id del usuario de la tabla usuario para obtener los seguidores
     * @return the List<UsuarioDTO>
     * @version 1.0
     */
    @GetMapping("/usuariosSeguidores/{id}")
    @Timed
    public List<UsuarioDTO>usuariosSeguidores(@PathVariable Long id) {
        List<SeguidorDTO> listaIds = seguidorCustomService.findAllByUserId(id);
        ArrayList<UsuarioDTO> seguidores = new ArrayList<>();
        for(int i=0;i<listaIds.size();i++){
            Long indice = listaIds.get(i).getIdSeguidorId();
           UsuarioDTO usuarioDTO = usuarioService.findOne(indice);
           seguidores.add(usuarioDTO);
        }
        return seguidores;
    }

    /**
     * GET  /usuariosSeguidos/{id} :
     * @author Maureen Leon
     * @param id del usuario  obtiene todos los seguimientos que tienen el id del usuario como seguidor y envia obtener los usuarios
     * seguidos
     * @return the List<UsuarioDTO>
     * @version 1.0
     */
    @GetMapping("/usuariosSeguidos/{id}")
    @Timed
    public List<UsuarioDTO>usuariosSeguidos(@PathVariable Long id) {
        List<SeguidorDTO> listaIds = seguidorCustomService.findAllSeguidosByUserId(id);
        ArrayList<UsuarioDTO> seguidores = new ArrayList<>();
        for(int i=0;i<listaIds.size();i++){
            Long indice = listaIds.get(i).getIdSeguidoId();
            UsuarioDTO usuarioDTO = usuarioService.findOne(indice);
            seguidores.add(usuarioDTO);
        }
        return seguidores;
    }

    /**
     * GET  /usuariosNotificacion/{userids} :
     * @author Maureen Leon
     * @param userids recibe una lista de el id de los usuarios que han generado un notificacion para ser mostrados
     * @return the List<UsuarioDTO>
     * @version 1.0
     */
    @GetMapping("/usuariosNotificacion/{userids}")
    @Timed
    public List<UsuarioDTO> usuariosNotificacion(@PathVariable List<Long> userids) throws URISyntaxException {
        ArrayList<UsuarioDTO> listaUsuarios = new ArrayList<>();
        for(int i=0;i<userids.size();i++){
            UsuarioDTO user = usuarioService.findOne(userids.get(i));
            listaUsuarios.add(user);
        }
        return listaUsuarios;
    }

    /**
     * @author Christopher Sullivan
     * Obtiene todos los usuarios normales
     * @return the List<UsuarioDTO>
     * @version 1.0
     */
    @GetMapping("/getAllUsers")
    @Timed
    public List<UsuarioDTO>getAllUsers() {
        return usuarioCustomService.findAllWithValidState();
    }


    /**
     * GET  /obtenerJhiUser/:id : get the "usuario".
     * @author Eduardo Guerrero
     * @param id the id of the UsuarioDtp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the UsuarioDTO, or with status 404 (Not Found)
     * @version 1.0
     */
    @GetMapping("/obtenerJhiUser/{id}")
    @Timed
    public UsuarioDTO getJhiUser(@PathVariable Integer id) {
        log.debug("REST request to get Usuario : {}", id);
        UsuarioDTO usuarioDTO = usuarioCustomService.findJhiUserByIdUsuario(id);
        return usuarioDTO;
    }
}

