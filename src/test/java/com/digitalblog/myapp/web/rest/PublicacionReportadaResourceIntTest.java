package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.PublicacionReportada;
import com.digitalblog.myapp.repository.PublicacionReportadaRepository;
import com.digitalblog.myapp.service.PublicacionReportadaService;
import com.digitalblog.myapp.service.dto.PublicacionReportadaDTO;
import com.digitalblog.myapp.service.mapper.PublicacionReportadaMapper;
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
 * Test class for the PublicacionReportadaResource REST controller.
 *
 * @see PublicacionReportadaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class PublicacionReportadaResourceIntTest {

    private static final Integer DEFAULT_CANTIDAD_REPORTES = 1;
    private static final Integer UPDATED_CANTIDAD_REPORTES = 2;

    private static final Integer DEFAULT_ID_PUBLICACION = 1;
    private static final Integer UPDATED_ID_PUBLICACION = 2;

    @Autowired
    private PublicacionReportadaRepository publicacionReportadaRepository;

    @Autowired
    private PublicacionReportadaMapper publicacionReportadaMapper;

    @Autowired
    private PublicacionReportadaService publicacionReportadaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPublicacionReportadaMockMvc;

    private PublicacionReportada publicacionReportada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublicacionReportadaResource publicacionReportadaResource = new PublicacionReportadaResource(publicacionReportadaService);
        this.restPublicacionReportadaMockMvc = MockMvcBuilders.standaloneSetup(publicacionReportadaResource)
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
    public static PublicacionReportada createEntity(EntityManager em) {
        PublicacionReportada publicacionReportada = new PublicacionReportada()
            .cantidadReportes(DEFAULT_CANTIDAD_REPORTES)
            .idPublicacion(DEFAULT_ID_PUBLICACION);
        return publicacionReportada;
    }

    @Before
    public void initTest() {
        publicacionReportada = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicacionReportada() throws Exception {
        int databaseSizeBeforeCreate = publicacionReportadaRepository.findAll().size();

        // Create the PublicacionReportada
        PublicacionReportadaDTO publicacionReportadaDTO = publicacionReportadaMapper.toDto(publicacionReportada);
        restPublicacionReportadaMockMvc.perform(post("/api/publicacion-reportadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionReportadaDTO)))
            .andExpect(status().isCreated());

        // Validate the PublicacionReportada in the database
        List<PublicacionReportada> publicacionReportadaList = publicacionReportadaRepository.findAll();
        assertThat(publicacionReportadaList).hasSize(databaseSizeBeforeCreate + 1);
        PublicacionReportada testPublicacionReportada = publicacionReportadaList.get(publicacionReportadaList.size() - 1);
        assertThat(testPublicacionReportada.getCantidadReportes()).isEqualTo(DEFAULT_CANTIDAD_REPORTES);
        assertThat(testPublicacionReportada.getIdPublicacion()).isEqualTo(DEFAULT_ID_PUBLICACION);
    }

    @Test
    @Transactional
    public void createPublicacionReportadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicacionReportadaRepository.findAll().size();

        // Create the PublicacionReportada with an existing ID
        publicacionReportada.setId(1L);
        PublicacionReportadaDTO publicacionReportadaDTO = publicacionReportadaMapper.toDto(publicacionReportada);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicacionReportadaMockMvc.perform(post("/api/publicacion-reportadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionReportadaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublicacionReportada in the database
        List<PublicacionReportada> publicacionReportadaList = publicacionReportadaRepository.findAll();
        assertThat(publicacionReportadaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPublicacionReportadas() throws Exception {
        // Initialize the database
        publicacionReportadaRepository.saveAndFlush(publicacionReportada);

        // Get all the publicacionReportadaList
        restPublicacionReportadaMockMvc.perform(get("/api/publicacion-reportadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicacionReportada.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidadReportes").value(hasItem(DEFAULT_CANTIDAD_REPORTES)))
            .andExpect(jsonPath("$.[*].idPublicacion").value(hasItem(DEFAULT_ID_PUBLICACION)));
    }

    @Test
    @Transactional
    public void getPublicacionReportada() throws Exception {
        // Initialize the database
        publicacionReportadaRepository.saveAndFlush(publicacionReportada);

        // Get the publicacionReportada
        restPublicacionReportadaMockMvc.perform(get("/api/publicacion-reportadas/{id}", publicacionReportada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publicacionReportada.getId().intValue()))
            .andExpect(jsonPath("$.cantidadReportes").value(DEFAULT_CANTIDAD_REPORTES))
            .andExpect(jsonPath("$.idPublicacion").value(DEFAULT_ID_PUBLICACION));
    }

    @Test
    @Transactional
    public void getNonExistingPublicacionReportada() throws Exception {
        // Get the publicacionReportada
        restPublicacionReportadaMockMvc.perform(get("/api/publicacion-reportadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicacionReportada() throws Exception {
        // Initialize the database
        publicacionReportadaRepository.saveAndFlush(publicacionReportada);
        int databaseSizeBeforeUpdate = publicacionReportadaRepository.findAll().size();

        // Update the publicacionReportada
        PublicacionReportada updatedPublicacionReportada = publicacionReportadaRepository.findOne(publicacionReportada.getId());
        updatedPublicacionReportada
            .cantidadReportes(UPDATED_CANTIDAD_REPORTES)
            .idPublicacion(UPDATED_ID_PUBLICACION);
        PublicacionReportadaDTO publicacionReportadaDTO = publicacionReportadaMapper.toDto(updatedPublicacionReportada);

        restPublicacionReportadaMockMvc.perform(put("/api/publicacion-reportadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionReportadaDTO)))
            .andExpect(status().isOk());

        // Validate the PublicacionReportada in the database
        List<PublicacionReportada> publicacionReportadaList = publicacionReportadaRepository.findAll();
        assertThat(publicacionReportadaList).hasSize(databaseSizeBeforeUpdate);
        PublicacionReportada testPublicacionReportada = publicacionReportadaList.get(publicacionReportadaList.size() - 1);
        assertThat(testPublicacionReportada.getCantidadReportes()).isEqualTo(UPDATED_CANTIDAD_REPORTES);
        assertThat(testPublicacionReportada.getIdPublicacion()).isEqualTo(UPDATED_ID_PUBLICACION);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicacionReportada() throws Exception {
        int databaseSizeBeforeUpdate = publicacionReportadaRepository.findAll().size();

        // Create the PublicacionReportada
        PublicacionReportadaDTO publicacionReportadaDTO = publicacionReportadaMapper.toDto(publicacionReportada);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPublicacionReportadaMockMvc.perform(put("/api/publicacion-reportadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionReportadaDTO)))
            .andExpect(status().isCreated());

        // Validate the PublicacionReportada in the database
        List<PublicacionReportada> publicacionReportadaList = publicacionReportadaRepository.findAll();
        assertThat(publicacionReportadaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePublicacionReportada() throws Exception {
        // Initialize the database
        publicacionReportadaRepository.saveAndFlush(publicacionReportada);
        int databaseSizeBeforeDelete = publicacionReportadaRepository.findAll().size();

        // Get the publicacionReportada
        restPublicacionReportadaMockMvc.perform(delete("/api/publicacion-reportadas/{id}", publicacionReportada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PublicacionReportada> publicacionReportadaList = publicacionReportadaRepository.findAll();
        assertThat(publicacionReportadaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicacionReportada.class);
        PublicacionReportada publicacionReportada1 = new PublicacionReportada();
        publicacionReportada1.setId(1L);
        PublicacionReportada publicacionReportada2 = new PublicacionReportada();
        publicacionReportada2.setId(publicacionReportada1.getId());
        assertThat(publicacionReportada1).isEqualTo(publicacionReportada2);
        publicacionReportada2.setId(2L);
        assertThat(publicacionReportada1).isNotEqualTo(publicacionReportada2);
        publicacionReportada1.setId(null);
        assertThat(publicacionReportada1).isNotEqualTo(publicacionReportada2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicacionReportadaDTO.class);
        PublicacionReportadaDTO publicacionReportadaDTO1 = new PublicacionReportadaDTO();
        publicacionReportadaDTO1.setId(1L);
        PublicacionReportadaDTO publicacionReportadaDTO2 = new PublicacionReportadaDTO();
        assertThat(publicacionReportadaDTO1).isNotEqualTo(publicacionReportadaDTO2);
        publicacionReportadaDTO2.setId(publicacionReportadaDTO1.getId());
        assertThat(publicacionReportadaDTO1).isEqualTo(publicacionReportadaDTO2);
        publicacionReportadaDTO2.setId(2L);
        assertThat(publicacionReportadaDTO1).isNotEqualTo(publicacionReportadaDTO2);
        publicacionReportadaDTO1.setId(null);
        assertThat(publicacionReportadaDTO1).isNotEqualTo(publicacionReportadaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(publicacionReportadaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(publicacionReportadaMapper.fromId(null)).isNull();
    }
}
