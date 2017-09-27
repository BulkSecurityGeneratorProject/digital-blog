package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.ImagenPorPublicacion;
import com.digitalblog.myapp.repository.ImagenPorPublicacionRepository;
import com.digitalblog.myapp.service.ImagenPorPublicacionService;
import com.digitalblog.myapp.service.dto.ImagenPorPublicacionDTO;
import com.digitalblog.myapp.service.mapper.ImagenPorPublicacionMapper;
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
 * Test class for the ImagenPorPublicacionResource REST controller.
 *
 * @see ImagenPorPublicacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class ImagenPorPublicacionResourceIntTest {

    private static final Integer DEFAULT_ID_PUBLICACION = 1;
    private static final Integer UPDATED_ID_PUBLICACION = 2;

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    @Autowired
    private ImagenPorPublicacionRepository imagenPorPublicacionRepository;

    @Autowired
    private ImagenPorPublicacionMapper imagenPorPublicacionMapper;

    @Autowired
    private ImagenPorPublicacionService imagenPorPublicacionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImagenPorPublicacionMockMvc;

    private ImagenPorPublicacion imagenPorPublicacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImagenPorPublicacionResource imagenPorPublicacionResource = new ImagenPorPublicacionResource(imagenPorPublicacionService);
        this.restImagenPorPublicacionMockMvc = MockMvcBuilders.standaloneSetup(imagenPorPublicacionResource)
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
    public static ImagenPorPublicacion createEntity(EntityManager em) {
        ImagenPorPublicacion imagenPorPublicacion = new ImagenPorPublicacion()
            .idPublicacion(DEFAULT_ID_PUBLICACION)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
        return imagenPorPublicacion;
    }

    @Before
    public void initTest() {
        imagenPorPublicacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createImagenPorPublicacion() throws Exception {
        int databaseSizeBeforeCreate = imagenPorPublicacionRepository.findAll().size();

        // Create the ImagenPorPublicacion
        ImagenPorPublicacionDTO imagenPorPublicacionDTO = imagenPorPublicacionMapper.toDto(imagenPorPublicacion);
        restImagenPorPublicacionMockMvc.perform(post("/api/imagen-por-publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagenPorPublicacionDTO)))
            .andExpect(status().isCreated());

        // Validate the ImagenPorPublicacion in the database
        List<ImagenPorPublicacion> imagenPorPublicacionList = imagenPorPublicacionRepository.findAll();
        assertThat(imagenPorPublicacionList).hasSize(databaseSizeBeforeCreate + 1);
        ImagenPorPublicacion testImagenPorPublicacion = imagenPorPublicacionList.get(imagenPorPublicacionList.size() - 1);
        assertThat(testImagenPorPublicacion.getIdPublicacion()).isEqualTo(DEFAULT_ID_PUBLICACION);
        assertThat(testImagenPorPublicacion.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testImagenPorPublicacion.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createImagenPorPublicacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imagenPorPublicacionRepository.findAll().size();

        // Create the ImagenPorPublicacion with an existing ID
        imagenPorPublicacion.setId(1L);
        ImagenPorPublicacionDTO imagenPorPublicacionDTO = imagenPorPublicacionMapper.toDto(imagenPorPublicacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagenPorPublicacionMockMvc.perform(post("/api/imagen-por-publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagenPorPublicacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImagenPorPublicacion in the database
        List<ImagenPorPublicacion> imagenPorPublicacionList = imagenPorPublicacionRepository.findAll();
        assertThat(imagenPorPublicacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImagenPorPublicacions() throws Exception {
        // Initialize the database
        imagenPorPublicacionRepository.saveAndFlush(imagenPorPublicacion);

        // Get all the imagenPorPublicacionList
        restImagenPorPublicacionMockMvc.perform(get("/api/imagen-por-publicacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imagenPorPublicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPublicacion").value(hasItem(DEFAULT_ID_PUBLICACION)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
    }

    @Test
    @Transactional
    public void getImagenPorPublicacion() throws Exception {
        // Initialize the database
        imagenPorPublicacionRepository.saveAndFlush(imagenPorPublicacion);

        // Get the imagenPorPublicacion
        restImagenPorPublicacionMockMvc.perform(get("/api/imagen-por-publicacions/{id}", imagenPorPublicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imagenPorPublicacion.getId().intValue()))
            .andExpect(jsonPath("$.idPublicacion").value(DEFAULT_ID_PUBLICACION))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)));
    }

    @Test
    @Transactional
    public void getNonExistingImagenPorPublicacion() throws Exception {
        // Get the imagenPorPublicacion
        restImagenPorPublicacionMockMvc.perform(get("/api/imagen-por-publicacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImagenPorPublicacion() throws Exception {
        // Initialize the database
        imagenPorPublicacionRepository.saveAndFlush(imagenPorPublicacion);
        int databaseSizeBeforeUpdate = imagenPorPublicacionRepository.findAll().size();

        // Update the imagenPorPublicacion
        ImagenPorPublicacion updatedImagenPorPublicacion = imagenPorPublicacionRepository.findOne(imagenPorPublicacion.getId());
        updatedImagenPorPublicacion
            .idPublicacion(UPDATED_ID_PUBLICACION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
        ImagenPorPublicacionDTO imagenPorPublicacionDTO = imagenPorPublicacionMapper.toDto(updatedImagenPorPublicacion);

        restImagenPorPublicacionMockMvc.perform(put("/api/imagen-por-publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagenPorPublicacionDTO)))
            .andExpect(status().isOk());

        // Validate the ImagenPorPublicacion in the database
        List<ImagenPorPublicacion> imagenPorPublicacionList = imagenPorPublicacionRepository.findAll();
        assertThat(imagenPorPublicacionList).hasSize(databaseSizeBeforeUpdate);
        ImagenPorPublicacion testImagenPorPublicacion = imagenPorPublicacionList.get(imagenPorPublicacionList.size() - 1);
        assertThat(testImagenPorPublicacion.getIdPublicacion()).isEqualTo(UPDATED_ID_PUBLICACION);
        assertThat(testImagenPorPublicacion.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testImagenPorPublicacion.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingImagenPorPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = imagenPorPublicacionRepository.findAll().size();

        // Create the ImagenPorPublicacion
        ImagenPorPublicacionDTO imagenPorPublicacionDTO = imagenPorPublicacionMapper.toDto(imagenPorPublicacion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImagenPorPublicacionMockMvc.perform(put("/api/imagen-por-publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagenPorPublicacionDTO)))
            .andExpect(status().isCreated());

        // Validate the ImagenPorPublicacion in the database
        List<ImagenPorPublicacion> imagenPorPublicacionList = imagenPorPublicacionRepository.findAll();
        assertThat(imagenPorPublicacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImagenPorPublicacion() throws Exception {
        // Initialize the database
        imagenPorPublicacionRepository.saveAndFlush(imagenPorPublicacion);
        int databaseSizeBeforeDelete = imagenPorPublicacionRepository.findAll().size();

        // Get the imagenPorPublicacion
        restImagenPorPublicacionMockMvc.perform(delete("/api/imagen-por-publicacions/{id}", imagenPorPublicacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ImagenPorPublicacion> imagenPorPublicacionList = imagenPorPublicacionRepository.findAll();
        assertThat(imagenPorPublicacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImagenPorPublicacion.class);
        ImagenPorPublicacion imagenPorPublicacion1 = new ImagenPorPublicacion();
        imagenPorPublicacion1.setId(1L);
        ImagenPorPublicacion imagenPorPublicacion2 = new ImagenPorPublicacion();
        imagenPorPublicacion2.setId(imagenPorPublicacion1.getId());
        assertThat(imagenPorPublicacion1).isEqualTo(imagenPorPublicacion2);
        imagenPorPublicacion2.setId(2L);
        assertThat(imagenPorPublicacion1).isNotEqualTo(imagenPorPublicacion2);
        imagenPorPublicacion1.setId(null);
        assertThat(imagenPorPublicacion1).isNotEqualTo(imagenPorPublicacion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImagenPorPublicacionDTO.class);
        ImagenPorPublicacionDTO imagenPorPublicacionDTO1 = new ImagenPorPublicacionDTO();
        imagenPorPublicacionDTO1.setId(1L);
        ImagenPorPublicacionDTO imagenPorPublicacionDTO2 = new ImagenPorPublicacionDTO();
        assertThat(imagenPorPublicacionDTO1).isNotEqualTo(imagenPorPublicacionDTO2);
        imagenPorPublicacionDTO2.setId(imagenPorPublicacionDTO1.getId());
        assertThat(imagenPorPublicacionDTO1).isEqualTo(imagenPorPublicacionDTO2);
        imagenPorPublicacionDTO2.setId(2L);
        assertThat(imagenPorPublicacionDTO1).isNotEqualTo(imagenPorPublicacionDTO2);
        imagenPorPublicacionDTO1.setId(null);
        assertThat(imagenPorPublicacionDTO1).isNotEqualTo(imagenPorPublicacionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(imagenPorPublicacionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(imagenPorPublicacionMapper.fromId(null)).isNull();
    }
}
