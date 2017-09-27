package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.Coolaborador;
import com.digitalblog.myapp.repository.CoolaboradorRepository;
import com.digitalblog.myapp.service.CoolaboradorService;
import com.digitalblog.myapp.service.dto.CoolaboradorDTO;
import com.digitalblog.myapp.service.mapper.CoolaboradorMapper;
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
 * Test class for the CoolaboradorResource REST controller.
 *
 * @see CoolaboradorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class CoolaboradorResourceIntTest {

    @Autowired
    private CoolaboradorRepository coolaboradorRepository;

    @Autowired
    private CoolaboradorMapper coolaboradorMapper;

    @Autowired
    private CoolaboradorService coolaboradorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoolaboradorMockMvc;

    private Coolaborador coolaborador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoolaboradorResource coolaboradorResource = new CoolaboradorResource(coolaboradorService);
        this.restCoolaboradorMockMvc = MockMvcBuilders.standaloneSetup(coolaboradorResource)
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
    public static Coolaborador createEntity(EntityManager em) {
        Coolaborador coolaborador = new Coolaborador();
        return coolaborador;
    }

    @Before
    public void initTest() {
        coolaborador = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoolaborador() throws Exception {
        int databaseSizeBeforeCreate = coolaboradorRepository.findAll().size();

        // Create the Coolaborador
        CoolaboradorDTO coolaboradorDTO = coolaboradorMapper.toDto(coolaborador);
        restCoolaboradorMockMvc.perform(post("/api/coolaboradors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coolaboradorDTO)))
            .andExpect(status().isCreated());

        // Validate the Coolaborador in the database
        List<Coolaborador> coolaboradorList = coolaboradorRepository.findAll();
        assertThat(coolaboradorList).hasSize(databaseSizeBeforeCreate + 1);
        Coolaborador testCoolaborador = coolaboradorList.get(coolaboradorList.size() - 1);
    }

    @Test
    @Transactional
    public void createCoolaboradorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coolaboradorRepository.findAll().size();

        // Create the Coolaborador with an existing ID
        coolaborador.setId(1L);
        CoolaboradorDTO coolaboradorDTO = coolaboradorMapper.toDto(coolaborador);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoolaboradorMockMvc.perform(post("/api/coolaboradors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coolaboradorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coolaborador in the database
        List<Coolaborador> coolaboradorList = coolaboradorRepository.findAll();
        assertThat(coolaboradorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCoolaboradors() throws Exception {
        // Initialize the database
        coolaboradorRepository.saveAndFlush(coolaborador);

        // Get all the coolaboradorList
        restCoolaboradorMockMvc.perform(get("/api/coolaboradors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coolaborador.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCoolaborador() throws Exception {
        // Initialize the database
        coolaboradorRepository.saveAndFlush(coolaborador);

        // Get the coolaborador
        restCoolaboradorMockMvc.perform(get("/api/coolaboradors/{id}", coolaborador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coolaborador.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoolaborador() throws Exception {
        // Get the coolaborador
        restCoolaboradorMockMvc.perform(get("/api/coolaboradors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoolaborador() throws Exception {
        // Initialize the database
        coolaboradorRepository.saveAndFlush(coolaborador);
        int databaseSizeBeforeUpdate = coolaboradorRepository.findAll().size();

        // Update the coolaborador
        Coolaborador updatedCoolaborador = coolaboradorRepository.findOne(coolaborador.getId());
        CoolaboradorDTO coolaboradorDTO = coolaboradorMapper.toDto(updatedCoolaborador);

        restCoolaboradorMockMvc.perform(put("/api/coolaboradors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coolaboradorDTO)))
            .andExpect(status().isOk());

        // Validate the Coolaborador in the database
        List<Coolaborador> coolaboradorList = coolaboradorRepository.findAll();
        assertThat(coolaboradorList).hasSize(databaseSizeBeforeUpdate);
        Coolaborador testCoolaborador = coolaboradorList.get(coolaboradorList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCoolaborador() throws Exception {
        int databaseSizeBeforeUpdate = coolaboradorRepository.findAll().size();

        // Create the Coolaborador
        CoolaboradorDTO coolaboradorDTO = coolaboradorMapper.toDto(coolaborador);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoolaboradorMockMvc.perform(put("/api/coolaboradors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coolaboradorDTO)))
            .andExpect(status().isCreated());

        // Validate the Coolaborador in the database
        List<Coolaborador> coolaboradorList = coolaboradorRepository.findAll();
        assertThat(coolaboradorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoolaborador() throws Exception {
        // Initialize the database
        coolaboradorRepository.saveAndFlush(coolaborador);
        int databaseSizeBeforeDelete = coolaboradorRepository.findAll().size();

        // Get the coolaborador
        restCoolaboradorMockMvc.perform(delete("/api/coolaboradors/{id}", coolaborador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Coolaborador> coolaboradorList = coolaboradorRepository.findAll();
        assertThat(coolaboradorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coolaborador.class);
        Coolaborador coolaborador1 = new Coolaborador();
        coolaborador1.setId(1L);
        Coolaborador coolaborador2 = new Coolaborador();
        coolaborador2.setId(coolaborador1.getId());
        assertThat(coolaborador1).isEqualTo(coolaborador2);
        coolaborador2.setId(2L);
        assertThat(coolaborador1).isNotEqualTo(coolaborador2);
        coolaborador1.setId(null);
        assertThat(coolaborador1).isNotEqualTo(coolaborador2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoolaboradorDTO.class);
        CoolaboradorDTO coolaboradorDTO1 = new CoolaboradorDTO();
        coolaboradorDTO1.setId(1L);
        CoolaboradorDTO coolaboradorDTO2 = new CoolaboradorDTO();
        assertThat(coolaboradorDTO1).isNotEqualTo(coolaboradorDTO2);
        coolaboradorDTO2.setId(coolaboradorDTO1.getId());
        assertThat(coolaboradorDTO1).isEqualTo(coolaboradorDTO2);
        coolaboradorDTO2.setId(2L);
        assertThat(coolaboradorDTO1).isNotEqualTo(coolaboradorDTO2);
        coolaboradorDTO1.setId(null);
        assertThat(coolaboradorDTO1).isNotEqualTo(coolaboradorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coolaboradorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coolaboradorMapper.fromId(null)).isNull();
    }
}
