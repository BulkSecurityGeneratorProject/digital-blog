package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.Usuario;
import com.digitalblog.myapp.repository.UsuarioRepository;
import com.digitalblog.myapp.service.UsuarioService;
import com.digitalblog.myapp.service.dto.UsuarioDTO;
import com.digitalblog.myapp.service.mapper.UsuarioMapper;
import com.digitalblog.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.digitalblog.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UsuarioResource REST controller.
 *
 * @see UsuarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class UsuarioResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLDIO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLDIO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final Integer DEFAULT_EDAD = 1;
    private static final Integer UPDATED_EDAD = 2;

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTOPERFIL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTOPERFIL = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTOPERFIL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTOPERFIL_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Long DEFAULT_ID_JHI_USER = 1L;
    private static final Long UPDATED_ID_JHI_USER = 2L;

    private static final ZonedDateTime DEFAULT_FECHA_NACIMIENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_NACIMIENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUsuarioMockMvc;

    private Usuario usuario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UsuarioResource usuarioResource = new UsuarioResource(usuarioService);
        this.restUsuarioMockMvc = MockMvcBuilders.standaloneSetup(usuarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createEntity(EntityManager em) {
        Usuario usuario = new Usuario()
                .nombre(DEFAULT_NOMBRE)
                .primerApelldio(DEFAULT_PRIMER_APELLDIO)
                .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
                .edad(DEFAULT_EDAD)
                .correo(DEFAULT_CORREO)
                .descripcion(DEFAULT_DESCRIPCION)
                .fotoperfil(DEFAULT_FOTOPERFIL)
                .fotoperfilContentType(DEFAULT_FOTOPERFIL_CONTENT_TYPE)
                .estado(DEFAULT_ESTADO)
                .idJHIUser(DEFAULT_ID_JHI_USER)
                .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO);
        return usuario;
    }

    @Before
    public void initTest() {
        usuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuario() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(usuario);

        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testUsuario.getPrimerApelldio()).isEqualTo(DEFAULT_PRIMER_APELLDIO);
        assertThat(testUsuario.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testUsuario.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testUsuario.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testUsuario.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testUsuario.getFotoperfil()).isEqualTo(DEFAULT_FOTOPERFIL);
        assertThat(testUsuario.getFotoperfilContentType()).isEqualTo(DEFAULT_FOTOPERFIL_CONTENT_TYPE);
        assertThat(testUsuario.isEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testUsuario.getIdJHIUser()).isEqualTo(DEFAULT_ID_JHI_USER);
        assertThat(testUsuario.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void createUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Create the Usuario with an existing ID
        Usuario existingUsuario = new Usuario();
        existingUsuario.setId(1L);
        UsuarioDTO existingUsuarioDTO = usuarioMapper.usuarioToUsuarioDTO(existingUsuario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUsuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUsuarios() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList
        restUsuarioMockMvc.perform(get("/api/usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].primerApelldio").value(hasItem(DEFAULT_PRIMER_APELLDIO.toString())))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fotoperfilContentType").value(hasItem(DEFAULT_FOTOPERFIL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotoperfil").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOPERFIL))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].idJHIUser").value(hasItem(DEFAULT_ID_JHI_USER.intValue())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(sameInstant(DEFAULT_FECHA_NACIMIENTO))));
    }

    @Test
    @Transactional
    public void getUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", usuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usuario.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.primerApelldio").value(DEFAULT_PRIMER_APELLDIO.toString()))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO.toString()))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fotoperfilContentType").value(DEFAULT_FOTOPERFIL_CONTENT_TYPE))
            .andExpect(jsonPath("$.fotoperfil").value(Base64Utils.encodeToString(DEFAULT_FOTOPERFIL)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.idJHIUser").value(DEFAULT_ID_JHI_USER.intValue()))
            .andExpect(jsonPath("$.fechaNacimiento").value(sameInstant(DEFAULT_FECHA_NACIMIENTO)));
    }

    @Test
    @Transactional
    public void getNonExistingUsuario() throws Exception {
        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario
        Usuario updatedUsuario = usuarioRepository.findOne(usuario.getId());
        updatedUsuario
                .nombre(UPDATED_NOMBRE)
                .primerApelldio(UPDATED_PRIMER_APELLDIO)
                .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
                .edad(UPDATED_EDAD)
                .correo(UPDATED_CORREO)
                .descripcion(UPDATED_DESCRIPCION)
                .fotoperfil(UPDATED_FOTOPERFIL)
                .fotoperfilContentType(UPDATED_FOTOPERFIL_CONTENT_TYPE)
                .estado(UPDATED_ESTADO)
                .idJHIUser(UPDATED_ID_JHI_USER)
                .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);
        UsuarioDTO usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(updatedUsuario);

        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testUsuario.getPrimerApelldio()).isEqualTo(UPDATED_PRIMER_APELLDIO);
        assertThat(testUsuario.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testUsuario.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testUsuario.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testUsuario.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testUsuario.getFotoperfil()).isEqualTo(UPDATED_FOTOPERFIL);
        assertThat(testUsuario.getFotoperfilContentType()).isEqualTo(UPDATED_FOTOPERFIL_CONTENT_TYPE);
        assertThat(testUsuario.isEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testUsuario.getIdJHIUser()).isEqualTo(UPDATED_ID_JHI_USER);
        assertThat(testUsuario.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(usuario);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);
        int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

        // Get the usuario
        restUsuarioMockMvc.perform(delete("/api/usuarios/{id}", usuario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usuario.class);
    }
}
