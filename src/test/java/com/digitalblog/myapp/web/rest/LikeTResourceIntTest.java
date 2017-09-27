package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.LikeT;
import com.digitalblog.myapp.repository.LikeTRepository;
import com.digitalblog.myapp.service.LikeTService;
import com.digitalblog.myapp.service.dto.LikeTDTO;
import com.digitalblog.myapp.service.mapper.LikeTMapper;
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
 * Test class for the LikeTResource REST controller.
 *
 * @see LikeTResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class LikeTResourceIntTest {

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    @Autowired
    private LikeTRepository likeTRepository;

    @Autowired
    private LikeTMapper likeTMapper;

    @Autowired
    private LikeTService likeTService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLikeTMockMvc;

    private LikeT likeT;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LikeTResource likeTResource = new LikeTResource(likeTService);
        this.restLikeTMockMvc = MockMvcBuilders.standaloneSetup(likeTResource)
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
    public static LikeT createEntity(EntityManager em) {
        LikeT likeT = new LikeT()
            .cantidad(DEFAULT_CANTIDAD);
        return likeT;
    }

    @Before
    public void initTest() {
        likeT = createEntity(em);
    }

    @Test
    @Transactional
    public void createLikeT() throws Exception {
        int databaseSizeBeforeCreate = likeTRepository.findAll().size();

        // Create the LikeT
        LikeTDTO likeTDTO = likeTMapper.toDto(likeT);
        restLikeTMockMvc.perform(post("/api/like-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeTDTO)))
            .andExpect(status().isCreated());

        // Validate the LikeT in the database
        List<LikeT> likeTList = likeTRepository.findAll();
        assertThat(likeTList).hasSize(databaseSizeBeforeCreate + 1);
        LikeT testLikeT = likeTList.get(likeTList.size() - 1);
        assertThat(testLikeT.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createLikeTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likeTRepository.findAll().size();

        // Create the LikeT with an existing ID
        likeT.setId(1L);
        LikeTDTO likeTDTO = likeTMapper.toDto(likeT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeTMockMvc.perform(post("/api/like-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LikeT in the database
        List<LikeT> likeTList = likeTRepository.findAll();
        assertThat(likeTList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLikeTS() throws Exception {
        // Initialize the database
        likeTRepository.saveAndFlush(likeT);

        // Get all the likeTList
        restLikeTMockMvc.perform(get("/api/like-ts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeT.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)));
    }

    @Test
    @Transactional
    public void getLikeT() throws Exception {
        // Initialize the database
        likeTRepository.saveAndFlush(likeT);

        // Get the likeT
        restLikeTMockMvc.perform(get("/api/like-ts/{id}", likeT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(likeT.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingLikeT() throws Exception {
        // Get the likeT
        restLikeTMockMvc.perform(get("/api/like-ts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLikeT() throws Exception {
        // Initialize the database
        likeTRepository.saveAndFlush(likeT);
        int databaseSizeBeforeUpdate = likeTRepository.findAll().size();

        // Update the likeT
        LikeT updatedLikeT = likeTRepository.findOne(likeT.getId());
        updatedLikeT
            .cantidad(UPDATED_CANTIDAD);
        LikeTDTO likeTDTO = likeTMapper.toDto(updatedLikeT);

        restLikeTMockMvc.perform(put("/api/like-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeTDTO)))
            .andExpect(status().isOk());

        // Validate the LikeT in the database
        List<LikeT> likeTList = likeTRepository.findAll();
        assertThat(likeTList).hasSize(databaseSizeBeforeUpdate);
        LikeT testLikeT = likeTList.get(likeTList.size() - 1);
        assertThat(testLikeT.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingLikeT() throws Exception {
        int databaseSizeBeforeUpdate = likeTRepository.findAll().size();

        // Create the LikeT
        LikeTDTO likeTDTO = likeTMapper.toDto(likeT);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLikeTMockMvc.perform(put("/api/like-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeTDTO)))
            .andExpect(status().isCreated());

        // Validate the LikeT in the database
        List<LikeT> likeTList = likeTRepository.findAll();
        assertThat(likeTList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLikeT() throws Exception {
        // Initialize the database
        likeTRepository.saveAndFlush(likeT);
        int databaseSizeBeforeDelete = likeTRepository.findAll().size();

        // Get the likeT
        restLikeTMockMvc.perform(delete("/api/like-ts/{id}", likeT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LikeT> likeTList = likeTRepository.findAll();
        assertThat(likeTList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikeT.class);
        LikeT likeT1 = new LikeT();
        likeT1.setId(1L);
        LikeT likeT2 = new LikeT();
        likeT2.setId(likeT1.getId());
        assertThat(likeT1).isEqualTo(likeT2);
        likeT2.setId(2L);
        assertThat(likeT1).isNotEqualTo(likeT2);
        likeT1.setId(null);
        assertThat(likeT1).isNotEqualTo(likeT2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikeTDTO.class);
        LikeTDTO likeTDTO1 = new LikeTDTO();
        likeTDTO1.setId(1L);
        LikeTDTO likeTDTO2 = new LikeTDTO();
        assertThat(likeTDTO1).isNotEqualTo(likeTDTO2);
        likeTDTO2.setId(likeTDTO1.getId());
        assertThat(likeTDTO1).isEqualTo(likeTDTO2);
        likeTDTO2.setId(2L);
        assertThat(likeTDTO1).isNotEqualTo(likeTDTO2);
        likeTDTO1.setId(null);
        assertThat(likeTDTO1).isNotEqualTo(likeTDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(likeTMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(likeTMapper.fromId(null)).isNull();
    }
}
