package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.Seguidor;
import com.digitalblog.myapp.repository.SeguidorRepository;
import com.digitalblog.myapp.service.SeguidorService;
import com.digitalblog.myapp.service.dto.SeguidorDTO;
import com.digitalblog.myapp.service.mapper.SeguidorMapper;
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
 * Test class for the SeguidorResource REST controller.
 *
 * @see SeguidorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class SeguidorResourceIntTest {

    private static final Boolean DEFAULT_ESTADO_SEGUIDOR = false;
    private static final Boolean UPDATED_ESTADO_SEGUIDOR = true;

    @Autowired
    private SeguidorRepository seguidorRepository;

    @Autowired
    private SeguidorMapper seguidorMapper;

    @Autowired
    private SeguidorService seguidorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeguidorMockMvc;

    private Seguidor seguidor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeguidorResource seguidorResource = new SeguidorResource(seguidorService);
        this.restSeguidorMockMvc = MockMvcBuilders.standaloneSetup(seguidorResource)
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
    public static Seguidor createEntity(EntityManager em) {
        Seguidor seguidor = new Seguidor()
            .estadoSeguidor(DEFAULT_ESTADO_SEGUIDOR);
        return seguidor;
    }

    @Before
    public void initTest() {
        seguidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeguidor() throws Exception {
        int databaseSizeBeforeCreate = seguidorRepository.findAll().size();

        // Create the Seguidor
        SeguidorDTO seguidorDTO = seguidorMapper.toDto(seguidor);
        restSeguidorMockMvc.perform(post("/api/seguidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguidorDTO)))
            .andExpect(status().isCreated());

        // Validate the Seguidor in the database
        List<Seguidor> seguidorList = seguidorRepository.findAll();
        assertThat(seguidorList).hasSize(databaseSizeBeforeCreate + 1);
        Seguidor testSeguidor = seguidorList.get(seguidorList.size() - 1);
        assertThat(testSeguidor.isEstadoSeguidor()).isEqualTo(DEFAULT_ESTADO_SEGUIDOR);
    }

    @Test
    @Transactional
    public void createSeguidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seguidorRepository.findAll().size();

        // Create the Seguidor with an existing ID
        seguidor.setId(1L);
        SeguidorDTO seguidorDTO = seguidorMapper.toDto(seguidor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeguidorMockMvc.perform(post("/api/seguidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguidorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seguidor in the database
        List<Seguidor> seguidorList = seguidorRepository.findAll();
        assertThat(seguidorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSeguidors() throws Exception {
        // Initialize the database
        seguidorRepository.saveAndFlush(seguidor);

        // Get all the seguidorList
        restSeguidorMockMvc.perform(get("/api/seguidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seguidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].estadoSeguidor").value(hasItem(DEFAULT_ESTADO_SEGUIDOR.booleanValue())));
    }

    @Test
    @Transactional
    public void getSeguidor() throws Exception {
        // Initialize the database
        seguidorRepository.saveAndFlush(seguidor);

        // Get the seguidor
        restSeguidorMockMvc.perform(get("/api/seguidors/{id}", seguidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seguidor.getId().intValue()))
            .andExpect(jsonPath("$.estadoSeguidor").value(DEFAULT_ESTADO_SEGUIDOR.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSeguidor() throws Exception {
        // Get the seguidor
        restSeguidorMockMvc.perform(get("/api/seguidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeguidor() throws Exception {
        // Initialize the database
        seguidorRepository.saveAndFlush(seguidor);
        int databaseSizeBeforeUpdate = seguidorRepository.findAll().size();

        // Update the seguidor
        Seguidor updatedSeguidor = seguidorRepository.findOne(seguidor.getId());
        updatedSeguidor
            .estadoSeguidor(UPDATED_ESTADO_SEGUIDOR);
        SeguidorDTO seguidorDTO = seguidorMapper.toDto(updatedSeguidor);

        restSeguidorMockMvc.perform(put("/api/seguidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguidorDTO)))
            .andExpect(status().isOk());

        // Validate the Seguidor in the database
        List<Seguidor> seguidorList = seguidorRepository.findAll();
        assertThat(seguidorList).hasSize(databaseSizeBeforeUpdate);
        Seguidor testSeguidor = seguidorList.get(seguidorList.size() - 1);
        assertThat(testSeguidor.isEstadoSeguidor()).isEqualTo(UPDATED_ESTADO_SEGUIDOR);
    }

    @Test
    @Transactional
    public void updateNonExistingSeguidor() throws Exception {
        int databaseSizeBeforeUpdate = seguidorRepository.findAll().size();

        // Create the Seguidor
        SeguidorDTO seguidorDTO = seguidorMapper.toDto(seguidor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeguidorMockMvc.perform(put("/api/seguidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguidorDTO)))
            .andExpect(status().isCreated());

        // Validate the Seguidor in the database
        List<Seguidor> seguidorList = seguidorRepository.findAll();
        assertThat(seguidorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeguidor() throws Exception {
        // Initialize the database
        seguidorRepository.saveAndFlush(seguidor);
        int databaseSizeBeforeDelete = seguidorRepository.findAll().size();

        // Get the seguidor
        restSeguidorMockMvc.perform(delete("/api/seguidors/{id}", seguidor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seguidor> seguidorList = seguidorRepository.findAll();
        assertThat(seguidorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seguidor.class);
        Seguidor seguidor1 = new Seguidor();
        seguidor1.setId(1L);
        Seguidor seguidor2 = new Seguidor();
        seguidor2.setId(seguidor1.getId());
        assertThat(seguidor1).isEqualTo(seguidor2);
        seguidor2.setId(2L);
        assertThat(seguidor1).isNotEqualTo(seguidor2);
        seguidor1.setId(null);
        assertThat(seguidor1).isNotEqualTo(seguidor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeguidorDTO.class);
        SeguidorDTO seguidorDTO1 = new SeguidorDTO();
        seguidorDTO1.setId(1L);
        SeguidorDTO seguidorDTO2 = new SeguidorDTO();
        assertThat(seguidorDTO1).isNotEqualTo(seguidorDTO2);
        seguidorDTO2.setId(seguidorDTO1.getId());
        assertThat(seguidorDTO1).isEqualTo(seguidorDTO2);
        seguidorDTO2.setId(2L);
        assertThat(seguidorDTO1).isNotEqualTo(seguidorDTO2);
        seguidorDTO1.setId(null);
        assertThat(seguidorDTO1).isNotEqualTo(seguidorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seguidorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seguidorMapper.fromId(null)).isNull();
    }
}
