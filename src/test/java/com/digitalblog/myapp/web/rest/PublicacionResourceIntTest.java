package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.Publicacion;
import com.digitalblog.myapp.repository.PublicacionRepository;
import com.digitalblog.myapp.service.PublicacionService;
import com.digitalblog.myapp.service.dto.PublicacionDTO;
import com.digitalblog.myapp.service.mapper.PublicacionMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PublicacionResource REST controller.
 *
 * @see PublicacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class PublicacionResourceIntTest {

    private static final String DEFAULT_URL_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_URL_IMAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TIPO = false;
    private static final Boolean UPDATED_TIPO = true;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTADO = 1;
    private static final Integer UPDATED_ESTADO = 2;

    private static final Integer DEFAULT_CANTIDAD_ITERACIONES = 1;
    private static final Integer UPDATED_CANTIDAD_ITERACIONES = 2;

    private static final byte[] DEFAULT_FOTOPUBLICACION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTOPUBLICACION = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTOPUBLICACION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTOPUBLICACION_CONTENT_TYPE = "image/png";

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private PublicacionMapper publicacionMapper;

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPublicacionMockMvc;

    private Publicacion publicacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublicacionResource publicacionResource = new PublicacionResource(publicacionService);
        this.restPublicacionMockMvc = MockMvcBuilders.standaloneSetup(publicacionResource)
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
    public static Publicacion createEntity(EntityManager em) {
        Publicacion publicacion = new Publicacion()
            .urlImagen(DEFAULT_URL_IMAGEN)
            .descripcion(DEFAULT_DESCRIPCION)
            .contenido(DEFAULT_CONTENIDO)
            .tipo(DEFAULT_TIPO)
            .titulo(DEFAULT_TITULO)
            .estado(DEFAULT_ESTADO)
            .cantidadIteraciones(DEFAULT_CANTIDAD_ITERACIONES)
            .fotopublicacion(DEFAULT_FOTOPUBLICACION)
            .fotopublicacionContentType(DEFAULT_FOTOPUBLICACION_CONTENT_TYPE);
        return publicacion;
    }

    @Before
    public void initTest() {
        publicacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicacion() throws Exception {
        int databaseSizeBeforeCreate = publicacionRepository.findAll().size();

        // Create the Publicacion
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);
        restPublicacionMockMvc.perform(post("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeCreate + 1);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getUrlImagen()).isEqualTo(DEFAULT_URL_IMAGEN);
        assertThat(testPublicacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPublicacion.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testPublicacion.isTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testPublicacion.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testPublicacion.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testPublicacion.getCantidadIteraciones()).isEqualTo(DEFAULT_CANTIDAD_ITERACIONES);
        assertThat(testPublicacion.getFotopublicacion()).isEqualTo(DEFAULT_FOTOPUBLICACION);
        assertThat(testPublicacion.getFotopublicacionContentType()).isEqualTo(DEFAULT_FOTOPUBLICACION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPublicacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicacionRepository.findAll().size();

        // Create the Publicacion with an existing ID
        publicacion.setId(1L);
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicacionMockMvc.perform(post("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPublicacions() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList
        restPublicacionMockMvc.perform(get("/api/publicacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlImagen").value(hasItem(DEFAULT_URL_IMAGEN.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.booleanValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].cantidadIteraciones").value(hasItem(DEFAULT_CANTIDAD_ITERACIONES)))
            .andExpect(jsonPath("$.[*].fotopublicacionContentType").value(hasItem(DEFAULT_FOTOPUBLICACION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotopublicacion").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOPUBLICACION))));
    }

    @Test
    @Transactional
    public void getPublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get the publicacion
        restPublicacionMockMvc.perform(get("/api/publicacions/{id}", publicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publicacion.getId().intValue()))
            .andExpect(jsonPath("$.urlImagen").value(DEFAULT_URL_IMAGEN.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.booleanValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.cantidadIteraciones").value(DEFAULT_CANTIDAD_ITERACIONES))
            .andExpect(jsonPath("$.fotopublicacionContentType").value(DEFAULT_FOTOPUBLICACION_CONTENT_TYPE))
            .andExpect(jsonPath("$.fotopublicacion").value(Base64Utils.encodeToString(DEFAULT_FOTOPUBLICACION)));
    }

    @Test
    @Transactional
    public void getNonExistingPublicacion() throws Exception {
        // Get the publicacion
        restPublicacionMockMvc.perform(get("/api/publicacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);
        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();

        // Update the publicacion
        Publicacion updatedPublicacion = publicacionRepository.findOne(publicacion.getId());
        updatedPublicacion
            .urlImagen(UPDATED_URL_IMAGEN)
            .descripcion(UPDATED_DESCRIPCION)
            .contenido(UPDATED_CONTENIDO)
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .estado(UPDATED_ESTADO)
            .cantidadIteraciones(UPDATED_CANTIDAD_ITERACIONES)
            .fotopublicacion(UPDATED_FOTOPUBLICACION)
            .fotopublicacionContentType(UPDATED_FOTOPUBLICACION_CONTENT_TYPE);
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(updatedPublicacion);

        restPublicacionMockMvc.perform(put("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionDTO)))
            .andExpect(status().isOk());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getUrlImagen()).isEqualTo(UPDATED_URL_IMAGEN);
        assertThat(testPublicacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPublicacion.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testPublicacion.isTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testPublicacion.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testPublicacion.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testPublicacion.getCantidadIteraciones()).isEqualTo(UPDATED_CANTIDAD_ITERACIONES);
        assertThat(testPublicacion.getFotopublicacion()).isEqualTo(UPDATED_FOTOPUBLICACION);
        assertThat(testPublicacion.getFotopublicacionContentType()).isEqualTo(UPDATED_FOTOPUBLICACION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();

        // Create the Publicacion
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPublicacionMockMvc.perform(put("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);
        int databaseSizeBeforeDelete = publicacionRepository.findAll().size();

        // Get the publicacion
        restPublicacionMockMvc.perform(delete("/api/publicacions/{id}", publicacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Publicacion.class);
        Publicacion publicacion1 = new Publicacion();
        publicacion1.setId(1L);
        Publicacion publicacion2 = new Publicacion();
        publicacion2.setId(publicacion1.getId());
        assertThat(publicacion1).isEqualTo(publicacion2);
        publicacion2.setId(2L);
        assertThat(publicacion1).isNotEqualTo(publicacion2);
        publicacion1.setId(null);
        assertThat(publicacion1).isNotEqualTo(publicacion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicacionDTO.class);
        PublicacionDTO publicacionDTO1 = new PublicacionDTO();
        publicacionDTO1.setId(1L);
        PublicacionDTO publicacionDTO2 = new PublicacionDTO();
        assertThat(publicacionDTO1).isNotEqualTo(publicacionDTO2);
        publicacionDTO2.setId(publicacionDTO1.getId());
        assertThat(publicacionDTO1).isEqualTo(publicacionDTO2);
        publicacionDTO2.setId(2L);
        assertThat(publicacionDTO1).isNotEqualTo(publicacionDTO2);
        publicacionDTO1.setId(null);
        assertThat(publicacionDTO1).isNotEqualTo(publicacionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(publicacionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(publicacionMapper.fromId(null)).isNull();
    }
}
