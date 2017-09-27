package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.SeccionPublicacion;
import com.digitalblog.myapp.repository.SeccionPublicacionRepository;
import com.digitalblog.myapp.service.SeccionPublicacionService;
import com.digitalblog.myapp.service.dto.SeccionPublicacionDTO;
import com.digitalblog.myapp.service.mapper.SeccionPublicacionMapper;
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
 * Test class for the SeccionPublicacionResource REST controller.
 *
 * @see SeccionPublicacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class SeccionPublicacionResourceIntTest {

    @Autowired
    private SeccionPublicacionRepository seccionPublicacionRepository;

    @Autowired
    private SeccionPublicacionMapper seccionPublicacionMapper;

    @Autowired
    private SeccionPublicacionService seccionPublicacionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeccionPublicacionMockMvc;

    private SeccionPublicacion seccionPublicacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeccionPublicacionResource seccionPublicacionResource = new SeccionPublicacionResource(seccionPublicacionService);
        this.restSeccionPublicacionMockMvc = MockMvcBuilders.standaloneSetup(seccionPublicacionResource)
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
    public static SeccionPublicacion createEntity(EntityManager em) {
        SeccionPublicacion seccionPublicacion = new SeccionPublicacion();
        return seccionPublicacion;
    }

    @Before
    public void initTest() {
        seccionPublicacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeccionPublicacion() throws Exception {
        int databaseSizeBeforeCreate = seccionPublicacionRepository.findAll().size();

        // Create the SeccionPublicacion
        SeccionPublicacionDTO seccionPublicacionDTO = seccionPublicacionMapper.seccionPublicacionToSeccionPublicacionDTO(seccionPublicacion);

        restSeccionPublicacionMockMvc.perform(post("/api/seccion-publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seccionPublicacionDTO)))
            .andExpect(status().isCreated());

        // Validate the SeccionPublicacion in the database
        List<SeccionPublicacion> seccionPublicacionList = seccionPublicacionRepository.findAll();
        assertThat(seccionPublicacionList).hasSize(databaseSizeBeforeCreate + 1);
        SeccionPublicacion testSeccionPublicacion = seccionPublicacionList.get(seccionPublicacionList.size() - 1);
    }

    @Test
    @Transactional
    public void createSeccionPublicacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seccionPublicacionRepository.findAll().size();

        // Create the SeccionPublicacion with an existing ID
        SeccionPublicacion existingSeccionPublicacion = new SeccionPublicacion();
        existingSeccionPublicacion.setId(1L);
        SeccionPublicacionDTO existingSeccionPublicacionDTO = seccionPublicacionMapper.seccionPublicacionToSeccionPublicacionDTO(existingSeccionPublicacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionPublicacionMockMvc.perform(post("/api/seccion-publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSeccionPublicacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SeccionPublicacion> seccionPublicacionList = seccionPublicacionRepository.findAll();
        assertThat(seccionPublicacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSeccionPublicacions() throws Exception {
        // Initialize the database
        seccionPublicacionRepository.saveAndFlush(seccionPublicacion);

        // Get all the seccionPublicacionList
        restSeccionPublicacionMockMvc.perform(get("/api/seccion-publicacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccionPublicacion.getId().intValue())));
    }

    @Test
    @Transactional
    public void getSeccionPublicacion() throws Exception {
        // Initialize the database
        seccionPublicacionRepository.saveAndFlush(seccionPublicacion);

        // Get the seccionPublicacion
        restSeccionPublicacionMockMvc.perform(get("/api/seccion-publicacions/{id}", seccionPublicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seccionPublicacion.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSeccionPublicacion() throws Exception {
        // Get the seccionPublicacion
        restSeccionPublicacionMockMvc.perform(get("/api/seccion-publicacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeccionPublicacion() throws Exception {
        // Initialize the database
        seccionPublicacionRepository.saveAndFlush(seccionPublicacion);
        int databaseSizeBeforeUpdate = seccionPublicacionRepository.findAll().size();

        // Update the seccionPublicacion
        SeccionPublicacion updatedSeccionPublicacion = seccionPublicacionRepository.findOne(seccionPublicacion.getId());
        SeccionPublicacionDTO seccionPublicacionDTO = seccionPublicacionMapper.seccionPublicacionToSeccionPublicacionDTO(updatedSeccionPublicacion);

        restSeccionPublicacionMockMvc.perform(put("/api/seccion-publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seccionPublicacionDTO)))
            .andExpect(status().isOk());

        // Validate the SeccionPublicacion in the database
        List<SeccionPublicacion> seccionPublicacionList = seccionPublicacionRepository.findAll();
        assertThat(seccionPublicacionList).hasSize(databaseSizeBeforeUpdate);
        SeccionPublicacion testSeccionPublicacion = seccionPublicacionList.get(seccionPublicacionList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSeccionPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = seccionPublicacionRepository.findAll().size();

        // Create the SeccionPublicacion
        SeccionPublicacionDTO seccionPublicacionDTO = seccionPublicacionMapper.seccionPublicacionToSeccionPublicacionDTO(seccionPublicacion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeccionPublicacionMockMvc.perform(put("/api/seccion-publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seccionPublicacionDTO)))
            .andExpect(status().isCreated());

        // Validate the SeccionPublicacion in the database
        List<SeccionPublicacion> seccionPublicacionList = seccionPublicacionRepository.findAll();
        assertThat(seccionPublicacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeccionPublicacion() throws Exception {
        // Initialize the database
        seccionPublicacionRepository.saveAndFlush(seccionPublicacion);
        int databaseSizeBeforeDelete = seccionPublicacionRepository.findAll().size();

        // Get the seccionPublicacion
        restSeccionPublicacionMockMvc.perform(delete("/api/seccion-publicacions/{id}", seccionPublicacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SeccionPublicacion> seccionPublicacionList = seccionPublicacionRepository.findAll();
        assertThat(seccionPublicacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeccionPublicacion.class);
    }
}
