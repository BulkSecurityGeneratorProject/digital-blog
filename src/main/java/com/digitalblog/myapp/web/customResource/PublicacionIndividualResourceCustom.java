package com.digitalblog.myapp.web.customResource;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.service.PublicacionService;
import com.digitalblog.myapp.service.SeccionPublicacionService;
import com.digitalblog.myapp.service.UsuarioService;
import com.digitalblog.myapp.service.customService.BibliotecaCustomService;
import com.digitalblog.myapp.service.customService.PublicacionCustomService;
import com.digitalblog.myapp.service.customService.SeccionCustomService;
import com.digitalblog.myapp.service.dto.*;
import com.digitalblog.myapp.web.rest.PublicacionResource;
import com.digitalblog.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing Publicacion.
 */
@RestController
@RequestMapping("/apiCustom")
public class PublicacionIndividualResourceCustom {

    private final Logger log = LoggerFactory.getLogger(PublicacionResource.class);

    private static final String ENTITY_NAME = "publicacion";

    private final PublicacionService publicacionService;

    private final PublicacionCustomService publicacionCustomService;

    private final UsuarioService usuarioService;

    private final BibliotecaCustomService bibliotecaCustomService;

    private final SeccionCustomService seccionCustomService;

    private final SeccionPublicacionService seccionPublicacionService;

    private Cloudinary cloudinary;

    public PublicacionIndividualResourceCustom(PublicacionService publicacionService, UsuarioService usuarioService, PublicacionCustomService publicacionCustomService, BibliotecaCustomService bibliotecaCustomService, SeccionCustomService seccionCustomService, SeccionPublicacionService seccionPublicacionService) {
        this.publicacionService = publicacionService;
        this.usuarioService = usuarioService;
        this.publicacionCustomService = publicacionCustomService;
        this.bibliotecaCustomService = bibliotecaCustomService;
        this.seccionCustomService = seccionCustomService;
        this.seccionPublicacionService = seccionPublicacionService;
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dblelmjha",
            "api_key", "592312911323426",
            "api_secret", "tPoLiNw8mpi9Qu5xkzEGx8vRKiQ"));
    }


    /**
     * @author Jose Nuñez
     * @param publicacionDTO
     * Método que hace update a la publicación individual, en este método se verifica la imagen, si no la tiene utiliza Cloudinary para setearla
     * @param publicacionDTO
     * @return PublicacionDTO
     * @throws URISyntaxException
     * @throws IOException
     * @version 1.0
     */
    @PutMapping("/crearPublicacionIn")
    @Timed
    public ResponseEntity<PublicacionDTO> updatePublicacionIndividual(@RequestBody PublicacionDTO publicacionDTO) throws URISyntaxException, IOException {
        log.debug("REST request to save Publicacion : {}", publicacionDTO);
        if (publicacionDTO.getId() != null) {
            return createPublicacionIndividual(publicacionDTO);
        }

        if (publicacionDTO.getFotopublicacion() != null) {
            publicacionDTO = cargarImagen(publicacionDTO);
        }
        PublicacionDTO result = publicacionService.save(publicacionDTO);
        return ResponseEntity.created(new URI("/api/publicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * @author Jose Nuñez
     * Método que crea la publicación individual, en este método se verifica la imagen, si no la tiene utilizaCloudinary para setearla
     * @param publicacionDTO
     * @return PublicacionDTO
     * @throws URISyntaxException
     * @throws IOException
     * @version 1.0
     */
    @PostMapping("/crearPublicacionIn")
    @Timed
    public ResponseEntity<PublicacionDTO> createPublicacionIndividual(@RequestBody PublicacionDTO publicacionDTO) throws URISyntaxException, IOException {
        log.debug("REST request to save Publicacion : {}", publicacionDTO);
        if (publicacionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new publicacion cannot already have an ID")).body(null);
        }

        if (publicacionDTO.getFotopublicacion() != null) {
            publicacionDTO = cargarImagen(publicacionDTO);
        }
        PublicacionDTO result = publicacionService.save(publicacionDTO);
        if (result.getEstado() == 1) {
            almacenarEnBorrador(result);
        }
        return ResponseEntity.created(new URI("/api/publicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * @author Jose nuñez
     * Metodo auxiliar
     * Carga imagen en cloudinary
     * @Param PublicacionDTO
     * @return PublicacionDTO
     * @version 1.0
     */
    private PublicacionDTO cargarImagen(PublicacionDTO publicacionDTO) throws IOException {

        byte[] bytes = publicacionDTO.getFotopublicacion();
        Map uploadResult = cloudinary.uploader().upload(bytes, ObjectUtils.emptyMap());
        Object url = uploadResult.get("url");
        publicacionDTO.setFotopublicacionContentType((String) url);
        publicacionDTO.setFotopublicacion(null);
        return publicacionDTO;

    }
    /**
     * @author Jose nuñez
     * Metodo auxiliar
     * almacenarEnBorrador almacena en la seccion mis publicaciones las publicaciones borrador que no han sido publicadas
     * @Param PublicacionDTO
     * @version 1.0
     */
    private void almacenarEnBorrador(PublicacionDTO publicacionDTO) {
        UsuarioDTO usuarioDTO = usuarioService.findOne(publicacionDTO.getUsuarioId());
        BibliotecaDTO bibliotecaDTO = bibliotecaCustomService.buscarBibliotecaPorJhiUser(usuarioDTO.getIdJHIUser());
        SeccionDTO seccionDTO = seccionCustomService.findSeccionByNameById(bibliotecaDTO.getId(), "Mis publicaciones");
        SeccionPublicacionDTO seccionPublicacionDTO = new SeccionPublicacionDTO();
        seccionPublicacionDTO.setIdPublicacionSPId(publicacionDTO.getId());
        seccionPublicacionDTO.setIdSeccionSPId(seccionDTO.getId());
        seccionPublicacionDTO = seccionPublicacionService.save(seccionPublicacionDTO);

    }

    /**
     * @author Maureen Leon
     * @Param Lista de PublicacionDTO pictures
     * @return List publicacionDTO
     * @version 1.0
     */
    @PostMapping("/savePictures/")
    @Timed
    public List<PublicacionDTO> usuariosNotificacion(@RequestBody List<PublicacionDTO> pictures) throws URISyntaxException, IOException {

        for (int i = 0; i < pictures.size(); i++) {

            byte[] bytes = pictures.get(i).getFotopublicacion();
            Map uploadResult = cloudinary.uploader().upload(bytes, ObjectUtils.emptyMap());
            Object url = uploadResult.get("url");
            pictures.get(i).setFotopublicacionContentType((String) url);
            pictures.get(i).setUrlImagen(url.toString());
        }
        return pictures;
    }

}



