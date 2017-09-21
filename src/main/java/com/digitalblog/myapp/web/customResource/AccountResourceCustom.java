package com.digitalblog.myapp.web.customResource;

import com.codahale.metrics.annotation.Timed;
import com.digitalblog.myapp.domain.User;
import com.digitalblog.myapp.repository.UserRepository;
import com.digitalblog.myapp.service.*;
import com.digitalblog.myapp.service.customService.UsuarioCustomService;
import com.digitalblog.myapp.service.dto.BibliotecaDTO;
import com.digitalblog.myapp.service.dto.SeccionDTO;
import com.digitalblog.myapp.service.dto.UsuarioDTO;
import com.digitalblog.myapp.web.rest.vm.ManagedUserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by jgm16 on 08/03/2017.
 */
@RestController
@RequestMapping("/apiCustom")
public class AccountResourceCustom {
    private final Logger log = LoggerFactory.getLogger(AccountResourceCustom.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    private final UsuarioService usuarioService;

    private final UsuarioCustomService usuarioCustomService;

    private final BibliotecaService bibliotecaService;

    private User users;

    private final SeccionService seccionService;


    public AccountResourceCustom(UserRepository userRepository, UserService userService,
                                 MailService mailService, UsuarioService usuarioService, UsuarioCustomService usuarioCustomService, BibliotecaService bibliotecaService, SeccionService seccionService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.usuarioService = usuarioService;
        this.usuarioCustomService = usuarioCustomService;
        this.bibliotecaService = bibliotecaService;
        this.seccionService = seccionService;
        users = new User();
    }

    /**
     * @autor Eduardo Guerrero
     * Metodo que registra la usuario, setea algunos datos del usuario normal,
     * Crea las secciones default de la biblioteca personal del usuario registrado
     * @param managedUserVM informacion del jhi user
     * @return ResponseEntity que ha sido creado
     * @version 1.0
     */
    @PostMapping(path = "/register",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        /**
         * Encuentra el usuario por login y el login se le hace lowerCase
         * Bad request si es 400 el email o el login ya se encuentran en uso
         */
        return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
            .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
                .map(user -> new ResponseEntity<>("e-mail address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    User user = userService
                        .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
                            managedUserVM.getFirstName(), managedUserVM.getLastName(),
                            managedUserVM.getEmail().toLowerCase(), managedUserVM.getImageUrl(), managedUserVM.getLangKey());
                    users = user;

                    /**
                     * Setea algunos atributos del jhi-user al usuario normal
                     */
                    UsuarioDTO usuario = this.usuarioCustomService.findByJhiUserId(managedUserVM.getId());
                    if (usuario == null) {
                        UsuarioDTO usuarioDTO = new UsuarioDTO();
                        usuarioDTO.setCorreo(users.getEmail());
                        usuarioDTO.setIdJHIUser(users.getId());
                        usuarioDTO.setFotoperfilContentType("../../../../content/images/avatar.jpeg");
                        usuarioDTO.setEstado(false);
                        usuarioDTO = usuarioService.save(usuarioDTO);
                    }
                    /**
                     * Crea las secciones por default de la biblioteca
                     */
                    BibliotecaDTO bibliotecaDTO = new BibliotecaDTO();
                    bibliotecaDTO.setIdJhiUser(user.getId());
                    bibliotecaDTO = bibliotecaService.save(bibliotecaDTO);

                    //Seccion custom 1
                    SeccionDTO seccionDTO = new SeccionDTO();
                    seccionDTO.setNombre("Por leer");
                    seccionDTO.setBibliotecaId(bibliotecaDTO.getId());
                    seccionService.save(seccionDTO);
                    //Seccion custom 2
                    seccionDTO.setNombre("Favoritos");
                    seccionDTO.setBibliotecaId(bibliotecaDTO.getId());
                    seccionService.save(seccionDTO);
                    //Seccion custom 3
                    seccionDTO.setNombre("Otros");
                    seccionDTO.setBibliotecaId(bibliotecaDTO.getId());
                    seccionService.save(seccionDTO);
                    //Seccion custom 4
                    seccionDTO.setNombre("Mis publicaciones");
                    seccionDTO.setBibliotecaId(bibliotecaDTO.getId());
                    seccionService.save(seccionDTO);
                    //Seccion custom 5
                    seccionDTO.setNombre("Reportes");
                    seccionDTO.setBibliotecaId(bibliotecaDTO.getId());
                    seccionService.save(seccionDTO);

                    mailService.sendActivationEmail(user);

                    return new ResponseEntity<>(HttpStatus.CREATED);
                })
            );
    }

    /**
     * @autor Eduardo Guerrero
     * Activa la cuenta del usuario nuevo
     * @param key recibe la clave de activacion para activar la cuenta
     * @return responsenntity 200 si la cuenta fue activada
     * @version 1.0
     */
    @GetMapping("/activate")
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        log.debug("Correo:" + users.getEmail());
        return userService.activateRegistration(key)
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

    }


}
