package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.Suscripciones;
import com.digitalblog.myapp.repository.SuscripcionesRepository;
import com.digitalblog.myapp.service.SuscripcionesService;
import com.digitalblog.myapp.service.dto.SuscripcionesDTO;
import com.digitalblog.myapp.service.mapper.SuscripcionesMapper;
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
 * Test class for the SuscripcionesResource REST controller.
 *
 * @see SuscripcionesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class SuscripcionesResourceIntTest {

    private static final Integer DEFAULT_ID_CANAL = 1;
    private static final Integer UPDATED_ID_CANAL = 2;

    private static final Integer DEFAULT_ID_SIGUIENDO = 1;
    private static final Integer UPDATED_ID_SIGUIENDO = 2;

    @Autowired
    private SuscripcionesRepository suscripcionesRepository;

    @Autowired
    private SuscripcionesMapper suscripcionesMapper;

    @Autowired
    private SuscripcionesService suscripcionesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSuscripcionesMockMvc;

    private Suscripciones suscripciones;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SuscripcionesResource suscripcionesResource = new SuscripcionesResource(suscripcionesService);
        this.restSuscripcionesMockMvc = MockMvcBuilders.standaloneSetup(suscripcionesResource)
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
    public static Suscripciones createEntity(EntityManager em) {
        Suscripciones suscripciones = new Suscripciones()
                .idCanal(DEFAULT_ID_CANAL)
                .idSiguiendo(DEFAULT_ID_SIGUIENDO);
        return suscripciones;
    }

    @Before
    public void initTest() {
        suscripciones = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuscripciones() throws Exception {
        int databaseSizeBeforeCreate = suscripcionesRepository.findAll().size();

        // Create the Suscripciones
        SuscripcionesDTO suscripcionesDTO = suscripcionesMapper.suscripcionesToSuscripcionesDTO(suscripciones);

        restSuscripcionesMockMvc.perform(post("/api/suscripciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suscripcionesDTO)))
            .andExpect(status().isCreated());

        // Validate the Suscripciones in the database
        List<Suscripciones> suscripcionesList = suscripcionesRepository.findAll();
        assertThat(suscripcionesList).hasSize(databaseSizeBeforeCreate + 1);
        Suscripciones testSuscripciones = suscripcionesList.get(suscripcionesList.size() - 1);
        assertThat(testSuscripciones.getIdCanal()).isEqualTo(DEFAULT_ID_CANAL);
        assertThat(testSuscripciones.getIdSiguiendo()).isEqualTo(DEFAULT_ID_SIGUIENDO);
    }

    @Test
    @Transactional
    public void createSuscripcionesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suscripcionesRepository.findAll().size();

        // Create the Suscripciones with an existing ID
        Suscripciones existingSuscripciones = new Suscripciones();
        existingSuscripciones.setId(1L);
        SuscripcionesDTO existingSuscripcionesDTO = suscripcionesMapper.suscripcionesToSuscripcionesDTO(existingSuscripciones);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuscripcionesMockMvc.perform(post("/api/suscripciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSuscripcionesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Suscripciones> suscripcionesList = suscripcionesRepository.findAll();
        assertThat(suscripcionesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSuscripciones() throws Exception {
        // Initialize the database
        suscripcionesRepository.saveAndFlush(suscripciones);

        // Get all the suscripcionesList
        restSuscripcionesMockMvc.perform(get("/api/suscripciones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suscripciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCanal").value(hasItem(DEFAULT_ID_CANAL)))
            .andExpect(jsonPath("$.[*].idSiguiendo").value(hasItem(DEFAULT_ID_SIGUIENDO)));
    }

    @Test
    @Transactional
    public void getSuscripciones() throws Exception {
        // Initialize the database
        suscripcionesRepository.saveAndFlush(suscripciones);

        // Get the suscripciones
        restSuscripcionesMockMvc.perform(get("/api/suscripciones/{id}", suscripciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suscripciones.getId().intValue()))
            .andExpect(jsonPath("$.idCanal").value(DEFAULT_ID_CANAL))
            .andExpect(jsonPath("$.idSiguiendo").value(DEFAULT_ID_SIGUIENDO));
    }

    @Test
    @Transactional
    public void getNonExistingSuscripciones() throws Exception {
        // Get the suscripciones
        restSuscripcionesMockMvc.perform(get("/api/suscripciones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuscripciones() throws Exception {
        // Initialize the database
        suscripcionesRepository.saveAndFlush(suscripciones);
        int databaseSizeBeforeUpdate = suscripcionesRepository.findAll().size();

        // Update the suscripciones
        Suscripciones updatedSuscripciones = suscripcionesRepository.findOne(suscripciones.getId());
        updatedSuscripciones
                .idCanal(UPDATED_ID_CANAL)
                .idSiguiendo(UPDATED_ID_SIGUIENDO);
        SuscripcionesDTO suscripcionesDTO = suscripcionesMapper.suscripcionesToSuscripcionesDTO(updatedSuscripciones);

        restSuscripcionesMockMvc.perform(put("/api/suscripciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suscripcionesDTO)))
            .andExpect(status().isOk());

        // Validate the Suscripciones in the database
        List<Suscripciones> suscripcionesList = suscripcionesRepository.findAll();
        assertThat(suscripcionesList).hasSize(databaseSizeBeforeUpdate);
        Suscripciones testSuscripciones = suscripcionesList.get(suscripcionesList.size() - 1);
        assertThat(testSuscripciones.getIdCanal()).isEqualTo(UPDATED_ID_CANAL);
        assertThat(testSuscripciones.getIdSiguiendo()).isEqualTo(UPDATED_ID_SIGUIENDO);
    }

    @Test
    @Transactional
    public void updateNonExistingSuscripciones() throws Exception {
        int databaseSizeBeforeUpdate = suscripcionesRepository.findAll().size();

        // Create the Suscripciones
        SuscripcionesDTO suscripcionesDTO = suscripcionesMapper.suscripcionesToSuscripcionesDTO(suscripciones);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSuscripcionesMockMvc.perform(put("/api/suscripciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suscripcionesDTO)))
            .andExpect(status().isCreated());

        // Validate the Suscripciones in the database
        List<Suscripciones> suscripcionesList = suscripcionesRepository.findAll();
        assertThat(suscripcionesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSuscripciones() throws Exception {
        // Initialize the database
        suscripcionesRepository.saveAndFlush(suscripciones);
        int databaseSizeBeforeDelete = suscripcionesRepository.findAll().size();

        // Get the suscripciones
        restSuscripcionesMockMvc.perform(delete("/api/suscripciones/{id}", suscripciones.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Suscripciones> suscripcionesList = suscripcionesRepository.findAll();
        assertThat(suscripcionesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suscripciones.class);
    }
}
