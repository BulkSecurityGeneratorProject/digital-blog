package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.Pagina;
import com.digitalblog.myapp.repository.PaginaRepository;
import com.digitalblog.myapp.service.PaginaService;
import com.digitalblog.myapp.service.dto.PaginaDTO;
import com.digitalblog.myapp.service.mapper.PaginaMapper;
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
 * Test class for the PaginaResource REST controller.
 *
 * @see PaginaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class PaginaResourceIntTest {

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_PAGINA = 1;
    private static final Integer UPDATED_NUMERO_PAGINA = 2;

    @Autowired
    private PaginaRepository paginaRepository;

    @Autowired
    private PaginaMapper paginaMapper;

    @Autowired
    private PaginaService paginaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaginaMockMvc;

    private Pagina pagina;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaginaResource paginaResource = new PaginaResource(paginaService);
        this.restPaginaMockMvc = MockMvcBuilders.standaloneSetup(paginaResource)
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
    public static Pagina createEntity(EntityManager em) {
        Pagina pagina = new Pagina()
            .contenido(DEFAULT_CONTENIDO)
            .numeroPagina(DEFAULT_NUMERO_PAGINA);
        return pagina;
    }

    @Before
    public void initTest() {
        pagina = createEntity(em);
    }

    @Test
    @Transactional
    public void createPagina() throws Exception {
        int databaseSizeBeforeCreate = paginaRepository.findAll().size();

        // Create the Pagina
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);
        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pagina in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeCreate + 1);
        Pagina testPagina = paginaList.get(paginaList.size() - 1);
        assertThat(testPagina.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testPagina.getNumeroPagina()).isEqualTo(DEFAULT_NUMERO_PAGINA);
    }

    @Test
    @Transactional
    public void createPaginaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paginaRepository.findAll().size();

        // Create the Pagina with an existing ID
        pagina.setId(1L);
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagina in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaginas() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);

        // Get all the paginaList
        restPaginaMockMvc.perform(get("/api/paginas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagina.getId().intValue())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].numeroPagina").value(hasItem(DEFAULT_NUMERO_PAGINA)));
    }

    @Test
    @Transactional
    public void getPagina() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);

        // Get the pagina
        restPaginaMockMvc.perform(get("/api/paginas/{id}", pagina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pagina.getId().intValue()))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()))
            .andExpect(jsonPath("$.numeroPagina").value(DEFAULT_NUMERO_PAGINA));
    }

    @Test
    @Transactional
    public void getNonExistingPagina() throws Exception {
        // Get the pagina
        restPaginaMockMvc.perform(get("/api/paginas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePagina() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);
        int databaseSizeBeforeUpdate = paginaRepository.findAll().size();

        // Update the pagina
        Pagina updatedPagina = paginaRepository.findOne(pagina.getId());
        updatedPagina
            .contenido(UPDATED_CONTENIDO)
            .numeroPagina(UPDATED_NUMERO_PAGINA);
        PaginaDTO paginaDTO = paginaMapper.toDto(updatedPagina);

        restPaginaMockMvc.perform(put("/api/paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isOk());

        // Validate the Pagina in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeUpdate);
        Pagina testPagina = paginaList.get(paginaList.size() - 1);
        assertThat(testPagina.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testPagina.getNumeroPagina()).isEqualTo(UPDATED_NUMERO_PAGINA);
    }

    @Test
    @Transactional
    public void updateNonExistingPagina() throws Exception {
        int databaseSizeBeforeUpdate = paginaRepository.findAll().size();

        // Create the Pagina
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaginaMockMvc.perform(put("/api/paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pagina in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePagina() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);
        int databaseSizeBeforeDelete = paginaRepository.findAll().size();

        // Get the pagina
        restPaginaMockMvc.perform(delete("/api/paginas/{id}", pagina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pagina.class);
        Pagina pagina1 = new Pagina();
        pagina1.setId(1L);
        Pagina pagina2 = new Pagina();
        pagina2.setId(pagina1.getId());
        assertThat(pagina1).isEqualTo(pagina2);
        pagina2.setId(2L);
        assertThat(pagina1).isNotEqualTo(pagina2);
        pagina1.setId(null);
        assertThat(pagina1).isNotEqualTo(pagina2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaginaDTO.class);
        PaginaDTO paginaDTO1 = new PaginaDTO();
        paginaDTO1.setId(1L);
        PaginaDTO paginaDTO2 = new PaginaDTO();
        assertThat(paginaDTO1).isNotEqualTo(paginaDTO2);
        paginaDTO2.setId(paginaDTO1.getId());
        assertThat(paginaDTO1).isEqualTo(paginaDTO2);
        paginaDTO2.setId(2L);
        assertThat(paginaDTO1).isNotEqualTo(paginaDTO2);
        paginaDTO1.setId(null);
        assertThat(paginaDTO1).isNotEqualTo(paginaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paginaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paginaMapper.fromId(null)).isNull();
    }
}
