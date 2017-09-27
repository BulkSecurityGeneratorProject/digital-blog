package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.Biblioteca;
import com.digitalblog.myapp.repository.BibliotecaRepository;
import com.digitalblog.myapp.service.BibliotecaService;
import com.digitalblog.myapp.service.dto.BibliotecaDTO;
import com.digitalblog.myapp.service.mapper.BibliotecaMapper;
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
 * Test class for the BibliotecaResource REST controller.
 *
 * @see BibliotecaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class BibliotecaResourceIntTest {

    private static final Integer DEFAULT_ID_SECCION = 1;
    private static final Integer UPDATED_ID_SECCION = 2;

    private static final Long DEFAULT_ID_JHI_USER = 1L;
    private static final Long UPDATED_ID_JHI_USER = 2L;

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @Autowired
    private BibliotecaMapper bibliotecaMapper;

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBibliotecaMockMvc;

    private Biblioteca biblioteca;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BibliotecaResource bibliotecaResource = new BibliotecaResource(bibliotecaService);
        this.restBibliotecaMockMvc = MockMvcBuilders.standaloneSetup(bibliotecaResource)
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
    public static Biblioteca createEntity(EntityManager em) {
        Biblioteca biblioteca = new Biblioteca()
            .idSeccion(DEFAULT_ID_SECCION)
            .idJhiUser(DEFAULT_ID_JHI_USER);
        return biblioteca;
    }

    @Before
    public void initTest() {
        biblioteca = createEntity(em);
    }

    @Test
    @Transactional
    public void createBiblioteca() throws Exception {
        int databaseSizeBeforeCreate = bibliotecaRepository.findAll().size();

        // Create the Biblioteca
        BibliotecaDTO bibliotecaDTO = bibliotecaMapper.toDto(biblioteca);
        restBibliotecaMockMvc.perform(post("/api/bibliotecas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bibliotecaDTO)))
            .andExpect(status().isCreated());

        // Validate the Biblioteca in the database
        List<Biblioteca> bibliotecaList = bibliotecaRepository.findAll();
        assertThat(bibliotecaList).hasSize(databaseSizeBeforeCreate + 1);
        Biblioteca testBiblioteca = bibliotecaList.get(bibliotecaList.size() - 1);
        assertThat(testBiblioteca.getIdSeccion()).isEqualTo(DEFAULT_ID_SECCION);
        assertThat(testBiblioteca.getIdJhiUser()).isEqualTo(DEFAULT_ID_JHI_USER);
    }

    @Test
    @Transactional
    public void createBibliotecaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bibliotecaRepository.findAll().size();

        // Create the Biblioteca with an existing ID
        biblioteca.setId(1L);
        BibliotecaDTO bibliotecaDTO = bibliotecaMapper.toDto(biblioteca);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBibliotecaMockMvc.perform(post("/api/bibliotecas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bibliotecaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Biblioteca in the database
        List<Biblioteca> bibliotecaList = bibliotecaRepository.findAll();
        assertThat(bibliotecaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBibliotecas() throws Exception {
        // Initialize the database
        bibliotecaRepository.saveAndFlush(biblioteca);

        // Get all the bibliotecaList
        restBibliotecaMockMvc.perform(get("/api/bibliotecas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biblioteca.getId().intValue())))
            .andExpect(jsonPath("$.[*].idSeccion").value(hasItem(DEFAULT_ID_SECCION)))
            .andExpect(jsonPath("$.[*].idJhiUser").value(hasItem(DEFAULT_ID_JHI_USER.intValue())));
    }

    @Test
    @Transactional
    public void getBiblioteca() throws Exception {
        // Initialize the database
        bibliotecaRepository.saveAndFlush(biblioteca);

        // Get the biblioteca
        restBibliotecaMockMvc.perform(get("/api/bibliotecas/{id}", biblioteca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(biblioteca.getId().intValue()))
            .andExpect(jsonPath("$.idSeccion").value(DEFAULT_ID_SECCION))
            .andExpect(jsonPath("$.idJhiUser").value(DEFAULT_ID_JHI_USER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBiblioteca() throws Exception {
        // Get the biblioteca
        restBibliotecaMockMvc.perform(get("/api/bibliotecas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBiblioteca() throws Exception {
        // Initialize the database
        bibliotecaRepository.saveAndFlush(biblioteca);
        int databaseSizeBeforeUpdate = bibliotecaRepository.findAll().size();

        // Update the biblioteca
        Biblioteca updatedBiblioteca = bibliotecaRepository.findOne(biblioteca.getId());
        updatedBiblioteca
            .idSeccion(UPDATED_ID_SECCION)
            .idJhiUser(UPDATED_ID_JHI_USER);
        BibliotecaDTO bibliotecaDTO = bibliotecaMapper.toDto(updatedBiblioteca);

        restBibliotecaMockMvc.perform(put("/api/bibliotecas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bibliotecaDTO)))
            .andExpect(status().isOk());

        // Validate the Biblioteca in the database
        List<Biblioteca> bibliotecaList = bibliotecaRepository.findAll();
        assertThat(bibliotecaList).hasSize(databaseSizeBeforeUpdate);
        Biblioteca testBiblioteca = bibliotecaList.get(bibliotecaList.size() - 1);
        assertThat(testBiblioteca.getIdSeccion()).isEqualTo(UPDATED_ID_SECCION);
        assertThat(testBiblioteca.getIdJhiUser()).isEqualTo(UPDATED_ID_JHI_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingBiblioteca() throws Exception {
        int databaseSizeBeforeUpdate = bibliotecaRepository.findAll().size();

        // Create the Biblioteca
        BibliotecaDTO bibliotecaDTO = bibliotecaMapper.toDto(biblioteca);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBibliotecaMockMvc.perform(put("/api/bibliotecas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bibliotecaDTO)))
            .andExpect(status().isCreated());

        // Validate the Biblioteca in the database
        List<Biblioteca> bibliotecaList = bibliotecaRepository.findAll();
        assertThat(bibliotecaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBiblioteca() throws Exception {
        // Initialize the database
        bibliotecaRepository.saveAndFlush(biblioteca);
        int databaseSizeBeforeDelete = bibliotecaRepository.findAll().size();

        // Get the biblioteca
        restBibliotecaMockMvc.perform(delete("/api/bibliotecas/{id}", biblioteca.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Biblioteca> bibliotecaList = bibliotecaRepository.findAll();
        assertThat(bibliotecaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Biblioteca.class);
        Biblioteca biblioteca1 = new Biblioteca();
        biblioteca1.setId(1L);
        Biblioteca biblioteca2 = new Biblioteca();
        biblioteca2.setId(biblioteca1.getId());
        assertThat(biblioteca1).isEqualTo(biblioteca2);
        biblioteca2.setId(2L);
        assertThat(biblioteca1).isNotEqualTo(biblioteca2);
        biblioteca1.setId(null);
        assertThat(biblioteca1).isNotEqualTo(biblioteca2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BibliotecaDTO.class);
        BibliotecaDTO bibliotecaDTO1 = new BibliotecaDTO();
        bibliotecaDTO1.setId(1L);
        BibliotecaDTO bibliotecaDTO2 = new BibliotecaDTO();
        assertThat(bibliotecaDTO1).isNotEqualTo(bibliotecaDTO2);
        bibliotecaDTO2.setId(bibliotecaDTO1.getId());
        assertThat(bibliotecaDTO1).isEqualTo(bibliotecaDTO2);
        bibliotecaDTO2.setId(2L);
        assertThat(bibliotecaDTO1).isNotEqualTo(bibliotecaDTO2);
        bibliotecaDTO1.setId(null);
        assertThat(bibliotecaDTO1).isNotEqualTo(bibliotecaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bibliotecaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bibliotecaMapper.fromId(null)).isNull();
    }
}
