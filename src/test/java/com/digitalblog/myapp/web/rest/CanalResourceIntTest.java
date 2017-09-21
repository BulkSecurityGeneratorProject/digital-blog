package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.Canal;
import com.digitalblog.myapp.repository.CanalRepository;
import com.digitalblog.myapp.service.CanalService;
import com.digitalblog.myapp.service.dto.CanalDTO;
import com.digitalblog.myapp.service.mapper.CanalMapper;
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
 * Test class for the CanalResource REST controller.
 *
 * @see CanalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class CanalResourceIntTest {

    private static final Integer DEFAULT_ID_USUARIO = 1;
    private static final Integer UPDATED_ID_USUARIO = 2;

    @Autowired
    private CanalRepository canalRepository;

    @Autowired
    private CanalMapper canalMapper;

    @Autowired
    private CanalService canalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCanalMockMvc;

    private Canal canal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CanalResource canalResource = new CanalResource(canalService);
        this.restCanalMockMvc = MockMvcBuilders.standaloneSetup(canalResource)
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
    public static Canal createEntity(EntityManager em) {
        Canal canal = new Canal()
                .idUsuario(DEFAULT_ID_USUARIO);
        return canal;
    }

    @Before
    public void initTest() {
        canal = createEntity(em);
    }

    @Test
    @Transactional
    public void createCanal() throws Exception {
        int databaseSizeBeforeCreate = canalRepository.findAll().size();

        // Create the Canal
        CanalDTO canalDTO = canalMapper.canalToCanalDTO(canal);

        restCanalMockMvc.perform(post("/api/canals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(canalDTO)))
            .andExpect(status().isCreated());

        // Validate the Canal in the database
        List<Canal> canalList = canalRepository.findAll();
        assertThat(canalList).hasSize(databaseSizeBeforeCreate + 1);
        Canal testCanal = canalList.get(canalList.size() - 1);
        assertThat(testCanal.getIdUsuario()).isEqualTo(DEFAULT_ID_USUARIO);
    }

    @Test
    @Transactional
    public void createCanalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = canalRepository.findAll().size();

        // Create the Canal with an existing ID
        Canal existingCanal = new Canal();
        existingCanal.setId(1L);
        CanalDTO existingCanalDTO = canalMapper.canalToCanalDTO(existingCanal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCanalMockMvc.perform(post("/api/canals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCanalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Canal> canalList = canalRepository.findAll();
        assertThat(canalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCanals() throws Exception {
        // Initialize the database
        canalRepository.saveAndFlush(canal);

        // Get all the canalList
        restCanalMockMvc.perform(get("/api/canals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(canal.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuario").value(hasItem(DEFAULT_ID_USUARIO)));
    }

    @Test
    @Transactional
    public void getCanal() throws Exception {
        // Initialize the database
        canalRepository.saveAndFlush(canal);

        // Get the canal
        restCanalMockMvc.perform(get("/api/canals/{id}", canal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(canal.getId().intValue()))
            .andExpect(jsonPath("$.idUsuario").value(DEFAULT_ID_USUARIO));
    }

    @Test
    @Transactional
    public void getNonExistingCanal() throws Exception {
        // Get the canal
        restCanalMockMvc.perform(get("/api/canals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCanal() throws Exception {
        // Initialize the database
        canalRepository.saveAndFlush(canal);
        int databaseSizeBeforeUpdate = canalRepository.findAll().size();

        // Update the canal
        Canal updatedCanal = canalRepository.findOne(canal.getId());
        updatedCanal
                .idUsuario(UPDATED_ID_USUARIO);
        CanalDTO canalDTO = canalMapper.canalToCanalDTO(updatedCanal);

        restCanalMockMvc.perform(put("/api/canals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(canalDTO)))
            .andExpect(status().isOk());

        // Validate the Canal in the database
        List<Canal> canalList = canalRepository.findAll();
        assertThat(canalList).hasSize(databaseSizeBeforeUpdate);
        Canal testCanal = canalList.get(canalList.size() - 1);
        assertThat(testCanal.getIdUsuario()).isEqualTo(UPDATED_ID_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingCanal() throws Exception {
        int databaseSizeBeforeUpdate = canalRepository.findAll().size();

        // Create the Canal
        CanalDTO canalDTO = canalMapper.canalToCanalDTO(canal);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCanalMockMvc.perform(put("/api/canals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(canalDTO)))
            .andExpect(status().isCreated());

        // Validate the Canal in the database
        List<Canal> canalList = canalRepository.findAll();
        assertThat(canalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCanal() throws Exception {
        // Initialize the database
        canalRepository.saveAndFlush(canal);
        int databaseSizeBeforeDelete = canalRepository.findAll().size();

        // Get the canal
        restCanalMockMvc.perform(delete("/api/canals/{id}", canal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Canal> canalList = canalRepository.findAll();
        assertThat(canalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Canal.class);
    }
}
