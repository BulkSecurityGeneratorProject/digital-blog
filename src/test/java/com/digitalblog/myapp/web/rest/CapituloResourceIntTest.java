package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.Capitulo;
import com.digitalblog.myapp.repository.CapituloRepository;
import com.digitalblog.myapp.service.CapituloService;
import com.digitalblog.myapp.service.dto.CapituloDTO;
import com.digitalblog.myapp.service.mapper.CapituloMapper;
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
 * Test class for the CapituloResource REST controller.
 *
 * @see CapituloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class CapituloResourceIntTest {

    private static final Integer DEFAULT_NUMERO_CAPITULO = 1;
    private static final Integer UPDATED_NUMERO_CAPITULO = 2;

    @Autowired
    private CapituloRepository capituloRepository;

    @Autowired
    private CapituloMapper capituloMapper;

    @Autowired
    private CapituloService capituloService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCapituloMockMvc;

    private Capitulo capitulo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CapituloResource capituloResource = new CapituloResource(capituloService);
        this.restCapituloMockMvc = MockMvcBuilders.standaloneSetup(capituloResource)
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
    public static Capitulo createEntity(EntityManager em) {
        Capitulo capitulo = new Capitulo()
                .numeroCapitulo(DEFAULT_NUMERO_CAPITULO);
        return capitulo;
    }

    @Before
    public void initTest() {
        capitulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCapitulo() throws Exception {
        int databaseSizeBeforeCreate = capituloRepository.findAll().size();

        // Create the Capitulo
        CapituloDTO capituloDTO = capituloMapper.capituloToCapituloDTO(capitulo);

        restCapituloMockMvc.perform(post("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capituloDTO)))
            .andExpect(status().isCreated());

        // Validate the Capitulo in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeCreate + 1);
        Capitulo testCapitulo = capituloList.get(capituloList.size() - 1);
        assertThat(testCapitulo.getNumeroCapitulo()).isEqualTo(DEFAULT_NUMERO_CAPITULO);
    }

    @Test
    @Transactional
    public void createCapituloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = capituloRepository.findAll().size();

        // Create the Capitulo with an existing ID
        Capitulo existingCapitulo = new Capitulo();
        existingCapitulo.setId(1L);
        CapituloDTO existingCapituloDTO = capituloMapper.capituloToCapituloDTO(existingCapitulo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapituloMockMvc.perform(post("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCapituloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCapitulos() throws Exception {
        // Initialize the database
        capituloRepository.saveAndFlush(capitulo);

        // Get all the capituloList
        restCapituloMockMvc.perform(get("/api/capitulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capitulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCapitulo").value(hasItem(DEFAULT_NUMERO_CAPITULO)));
    }

    @Test
    @Transactional
    public void getCapitulo() throws Exception {
        // Initialize the database
        capituloRepository.saveAndFlush(capitulo);

        // Get the capitulo
        restCapituloMockMvc.perform(get("/api/capitulos/{id}", capitulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(capitulo.getId().intValue()))
            .andExpect(jsonPath("$.numeroCapitulo").value(DEFAULT_NUMERO_CAPITULO));
    }

    @Test
    @Transactional
    public void getNonExistingCapitulo() throws Exception {
        // Get the capitulo
        restCapituloMockMvc.perform(get("/api/capitulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCapitulo() throws Exception {
        // Initialize the database
        capituloRepository.saveAndFlush(capitulo);
        int databaseSizeBeforeUpdate = capituloRepository.findAll().size();

        // Update the capitulo
        Capitulo updatedCapitulo = capituloRepository.findOne(capitulo.getId());
        updatedCapitulo
                .numeroCapitulo(UPDATED_NUMERO_CAPITULO);
        CapituloDTO capituloDTO = capituloMapper.capituloToCapituloDTO(updatedCapitulo);

        restCapituloMockMvc.perform(put("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capituloDTO)))
            .andExpect(status().isOk());

        // Validate the Capitulo in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeUpdate);
        Capitulo testCapitulo = capituloList.get(capituloList.size() - 1);
        assertThat(testCapitulo.getNumeroCapitulo()).isEqualTo(UPDATED_NUMERO_CAPITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingCapitulo() throws Exception {
        int databaseSizeBeforeUpdate = capituloRepository.findAll().size();

        // Create the Capitulo
        CapituloDTO capituloDTO = capituloMapper.capituloToCapituloDTO(capitulo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCapituloMockMvc.perform(put("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capituloDTO)))
            .andExpect(status().isCreated());

        // Validate the Capitulo in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCapitulo() throws Exception {
        // Initialize the database
        capituloRepository.saveAndFlush(capitulo);
        int databaseSizeBeforeDelete = capituloRepository.findAll().size();

        // Get the capitulo
        restCapituloMockMvc.perform(delete("/api/capitulos/{id}", capitulo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Capitulo.class);
    }
}
