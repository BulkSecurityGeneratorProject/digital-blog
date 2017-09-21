package com.digitalblog.myapp.web.rest;

import com.digitalblog.myapp.DigitalBlogApp;

import com.digitalblog.myapp.domain.RolePermiso;
import com.digitalblog.myapp.repository.RolePermisoRepository;
import com.digitalblog.myapp.service.RolePermisoService;
import com.digitalblog.myapp.service.dto.RolePermisoDTO;
import com.digitalblog.myapp.service.mapper.RolePermisoMapper;
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
 * Test class for the RolePermisoResource REST controller.
 *
 * @see RolePermisoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalBlogApp.class)
public class RolePermisoResourceIntTest {

    @Autowired
    private RolePermisoRepository rolePermisoRepository;

    @Autowired
    private RolePermisoMapper rolePermisoMapper;

    @Autowired
    private RolePermisoService rolePermisoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRolePermisoMockMvc;

    private RolePermiso rolePermiso;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RolePermisoResource rolePermisoResource = new RolePermisoResource(rolePermisoService);
        this.restRolePermisoMockMvc = MockMvcBuilders.standaloneSetup(rolePermisoResource)
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
    public static RolePermiso createEntity(EntityManager em) {
        RolePermiso rolePermiso = new RolePermiso();
        return rolePermiso;
    }

    @Before
    public void initTest() {
        rolePermiso = createEntity(em);
    }

    @Test
    @Transactional
    public void createRolePermiso() throws Exception {
        int databaseSizeBeforeCreate = rolePermisoRepository.findAll().size();

        // Create the RolePermiso
        RolePermisoDTO rolePermisoDTO = rolePermisoMapper.rolePermisoToRolePermisoDTO(rolePermiso);

        restRolePermisoMockMvc.perform(post("/api/role-permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolePermisoDTO)))
            .andExpect(status().isCreated());

        // Validate the RolePermiso in the database
        List<RolePermiso> rolePermisoList = rolePermisoRepository.findAll();
        assertThat(rolePermisoList).hasSize(databaseSizeBeforeCreate + 1);
        RolePermiso testRolePermiso = rolePermisoList.get(rolePermisoList.size() - 1);
    }

    @Test
    @Transactional
    public void createRolePermisoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rolePermisoRepository.findAll().size();

        // Create the RolePermiso with an existing ID
        RolePermiso existingRolePermiso = new RolePermiso();
        existingRolePermiso.setId(1L);
        RolePermisoDTO existingRolePermisoDTO = rolePermisoMapper.rolePermisoToRolePermisoDTO(existingRolePermiso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolePermisoMockMvc.perform(post("/api/role-permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRolePermisoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RolePermiso> rolePermisoList = rolePermisoRepository.findAll();
        assertThat(rolePermisoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRolePermisos() throws Exception {
        // Initialize the database
        rolePermisoRepository.saveAndFlush(rolePermiso);

        // Get all the rolePermisoList
        restRolePermisoMockMvc.perform(get("/api/role-permisos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rolePermiso.getId().intValue())));
    }

    @Test
    @Transactional
    public void getRolePermiso() throws Exception {
        // Initialize the database
        rolePermisoRepository.saveAndFlush(rolePermiso);

        // Get the rolePermiso
        restRolePermisoMockMvc.perform(get("/api/role-permisos/{id}", rolePermiso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rolePermiso.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRolePermiso() throws Exception {
        // Get the rolePermiso
        restRolePermisoMockMvc.perform(get("/api/role-permisos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRolePermiso() throws Exception {
        // Initialize the database
        rolePermisoRepository.saveAndFlush(rolePermiso);
        int databaseSizeBeforeUpdate = rolePermisoRepository.findAll().size();

        // Update the rolePermiso
        RolePermiso updatedRolePermiso = rolePermisoRepository.findOne(rolePermiso.getId());
        RolePermisoDTO rolePermisoDTO = rolePermisoMapper.rolePermisoToRolePermisoDTO(updatedRolePermiso);

        restRolePermisoMockMvc.perform(put("/api/role-permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolePermisoDTO)))
            .andExpect(status().isOk());

        // Validate the RolePermiso in the database
        List<RolePermiso> rolePermisoList = rolePermisoRepository.findAll();
        assertThat(rolePermisoList).hasSize(databaseSizeBeforeUpdate);
        RolePermiso testRolePermiso = rolePermisoList.get(rolePermisoList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingRolePermiso() throws Exception {
        int databaseSizeBeforeUpdate = rolePermisoRepository.findAll().size();

        // Create the RolePermiso
        RolePermisoDTO rolePermisoDTO = rolePermisoMapper.rolePermisoToRolePermisoDTO(rolePermiso);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRolePermisoMockMvc.perform(put("/api/role-permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolePermisoDTO)))
            .andExpect(status().isCreated());

        // Validate the RolePermiso in the database
        List<RolePermiso> rolePermisoList = rolePermisoRepository.findAll();
        assertThat(rolePermisoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRolePermiso() throws Exception {
        // Initialize the database
        rolePermisoRepository.saveAndFlush(rolePermiso);
        int databaseSizeBeforeDelete = rolePermisoRepository.findAll().size();

        // Get the rolePermiso
        restRolePermisoMockMvc.perform(delete("/api/role-permisos/{id}", rolePermiso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RolePermiso> rolePermisoList = rolePermisoRepository.findAll();
        assertThat(rolePermisoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RolePermiso.class);
    }
}
