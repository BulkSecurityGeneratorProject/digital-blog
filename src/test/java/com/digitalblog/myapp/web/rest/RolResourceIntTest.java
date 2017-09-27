package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.Rol;
import com.digitalblog.myapp.repository.RolRepository;
import com.digitalblog.myapp.service.RolService;
import com.digitalblog.myapp.service.dto.RolDTO;
import com.digitalblog.myapp.service.mapper.RolMapper;
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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RolResource REST controller.
 *
 * @see RolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class RolResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RolMapper rolMapper;

    @Autowired
    private RolService rolService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRolMockMvc;

    private Rol rol;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RolResource rolResource = new RolResource(rolService);
        this.restRolMockMvc = MockMvcBuilders.standaloneSetup(rolResource)
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
    public static Rol createEntity(EntityManager em) {
        Rol rol = new Rol()
                .nombre(DEFAULT_NOMBRE)
                .descripcion(DEFAULT_DESCRIPCION);
        return rol;
    }

    @Before
    public void initTest() {
        rol = createEntity(em);
    }

    @Test
    @Transactional
    public void createRol() throws Exception {
        int databaseSizeBeforeCreate = rolRepository.findAll().size();

        // Create the Rol
        RolDTO rolDTO = rolMapper.rolToRolDTO(rol);

        restRolMockMvc.perform(post("/api/rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolDTO)))
            .andExpect(status().isCreated());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeCreate + 1);
        Rol testRol = rolList.get(rolList.size() - 1);
        assertThat(testRol.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRol.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createRolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rolRepository.findAll().size();

        // Create the Rol with an existing ID
        Rol existingRol = new Rol();
        existingRol.setId(1L);
        RolDTO existingRolDTO = rolMapper.rolToRolDTO(existingRol);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolMockMvc.perform(post("/api/rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRols() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList
        restRolMockMvc.perform(get("/api/rols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rol.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get the rol
        restRolMockMvc.perform(get("/api/rols/{id}", rol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rol.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRol() throws Exception {
        // Get the rol
        restRolMockMvc.perform(get("/api/rols/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);
        int databaseSizeBeforeUpdate = rolRepository.findAll().size();

        // Update the rol
        Rol updatedRol = rolRepository.findOne(rol.getId());
        updatedRol
                .nombre(UPDATED_NOMBRE)
                .descripcion(UPDATED_DESCRIPCION);
        RolDTO rolDTO = rolMapper.rolToRolDTO(updatedRol);

        restRolMockMvc.perform(put("/api/rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolDTO)))
            .andExpect(status().isOk());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);
        Rol testRol = rolList.get(rolList.size() - 1);
        assertThat(testRol.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRol.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingRol() throws Exception {
        int databaseSizeBeforeUpdate = rolRepository.findAll().size();

        // Create the Rol
        RolDTO rolDTO = rolMapper.rolToRolDTO(rol);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRolMockMvc.perform(put("/api/rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolDTO)))
            .andExpect(status().isCreated());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);
        int databaseSizeBeforeDelete = rolRepository.findAll().size();

        // Get the rol
        restRolMockMvc.perform(delete("/api/rols/{id}", rol.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rol.class);
    }
}
