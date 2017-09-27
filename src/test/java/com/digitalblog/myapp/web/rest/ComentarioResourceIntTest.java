package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.Comentario;
import com.digitalblog.myapp.repository.ComentarioRepository;
import com.digitalblog.myapp.service.ComentarioService;
import com.digitalblog.myapp.service.dto.ComentarioDTO;
import com.digitalblog.myapp.service.mapper.ComentarioMapper;
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
 * Test class for the ComentarioResource REST controller.
 *
 * @see ComentarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class ComentarioResourceIntTest {

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ComentarioMapper comentarioMapper;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComentarioMockMvc;

    private Comentario comentario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComentarioResource comentarioResource = new ComentarioResource(comentarioService);
        this.restComentarioMockMvc = MockMvcBuilders.standaloneSetup(comentarioResource)
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
    public static Comentario createEntity(EntityManager em) {
        Comentario comentario = new Comentario()
                .contenido(DEFAULT_CONTENIDO);
        return comentario;
    }

    @Before
    public void initTest() {
        comentario = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentario() throws Exception {
        int databaseSizeBeforeCreate = comentarioRepository.findAll().size();

        // Create the Comentario
        ComentarioDTO comentarioDTO = comentarioMapper.comentarioToComentarioDTO(comentario);

        restComentarioMockMvc.perform(post("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeCreate + 1);
        Comentario testComentario = comentarioList.get(comentarioList.size() - 1);
        assertThat(testComentario.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
    }

    @Test
    @Transactional
    public void createComentarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentarioRepository.findAll().size();

        // Create the Comentario with an existing ID
        Comentario existingComentario = new Comentario();
        existingComentario.setId(1L);
        ComentarioDTO existingComentarioDTO = comentarioMapper.comentarioToComentarioDTO(existingComentario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentarioMockMvc.perform(post("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingComentarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComentarios() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        // Get all the comentarioList
        restComentarioMockMvc.perform(get("/api/comentarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentario.getId().intValue())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())));
    }

    @Test
    @Transactional
    public void getComentario() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        // Get the comentario
        restComentarioMockMvc.perform(get("/api/comentarios/{id}", comentario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comentario.getId().intValue()))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComentario() throws Exception {
        // Get the comentario
        restComentarioMockMvc.perform(get("/api/comentarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentario() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);
        int databaseSizeBeforeUpdate = comentarioRepository.findAll().size();

        // Update the comentario
        Comentario updatedComentario = comentarioRepository.findOne(comentario.getId());
        updatedComentario
                .contenido(UPDATED_CONTENIDO);
        ComentarioDTO comentarioDTO = comentarioMapper.comentarioToComentarioDTO(updatedComentario);

        restComentarioMockMvc.perform(put("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarioDTO)))
            .andExpect(status().isOk());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeUpdate);
        Comentario testComentario = comentarioList.get(comentarioList.size() - 1);
        assertThat(testComentario.getContenido()).isEqualTo(UPDATED_CONTENIDO);
    }

    @Test
    @Transactional
    public void updateNonExistingComentario() throws Exception {
        int databaseSizeBeforeUpdate = comentarioRepository.findAll().size();

        // Create the Comentario
        ComentarioDTO comentarioDTO = comentarioMapper.comentarioToComentarioDTO(comentario);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComentarioMockMvc.perform(put("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComentario() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);
        int databaseSizeBeforeDelete = comentarioRepository.findAll().size();

        // Get the comentario
        restComentarioMockMvc.perform(delete("/api/comentarios/{id}", comentario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comentario.class);
    }
}
