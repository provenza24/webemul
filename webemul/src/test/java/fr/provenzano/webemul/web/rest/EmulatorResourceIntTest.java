package fr.provenzano.webemul.web.rest;

import static fr.provenzano.webemul.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

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

import fr.provenzano.webemul.WebEmul;
import fr.provenzano.webemul.domain.Emulator;
import fr.provenzano.webemul.repository.EmulatorRepository;
import fr.provenzano.webemul.service.EmulatorService;
import fr.provenzano.webemul.service.dto.EmulatorDTO;
import fr.provenzano.webemul.service.mapper.EmulatorMapper;
import fr.provenzano.webemul.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the EmulatorResource REST controller.
 *
 * @see EmulatorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebEmul.class)
public class EmulatorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PATH_ICON = "AAAAAAAAAA";
    private static final String UPDATED_PATH_ICON = "BBBBBBBBBB";

    @Autowired
    private EmulatorRepository emulatorRepository;

    @Autowired
    private EmulatorMapper emulatorMapper;

    @Autowired
    private EmulatorService emulatorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmulatorMockMvc;

    private Emulator emulator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmulatorResource emulatorResource = new EmulatorResource(emulatorService);
        this.restEmulatorMockMvc = MockMvcBuilders.standaloneSetup(emulatorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emulator createEntity(EntityManager em) {
        Emulator emulator = new Emulator()
            .name(DEFAULT_NAME)
            .version(DEFAULT_VERSION)
            .pathIcon(DEFAULT_PATH_ICON);
        return emulator;
    }

    @Before
    public void initTest() {
        emulator = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmulator() throws Exception {
        int databaseSizeBeforeCreate = emulatorRepository.findAll().size();

        // Create the Emulator
        EmulatorDTO emulatorDTO = emulatorMapper.toDto(emulator);
        restEmulatorMockMvc.perform(post("/api/emulators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emulatorDTO)))
            .andExpect(status().isCreated());

        // Validate the Emulator in the database
        List<Emulator> emulatorList = emulatorRepository.findAll();
        assertThat(emulatorList).hasSize(databaseSizeBeforeCreate + 1);
        Emulator testEmulator = emulatorList.get(emulatorList.size() - 1);
        assertThat(testEmulator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmulator.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testEmulator.getPathIcon()).isEqualTo(DEFAULT_PATH_ICON);
    }

    @Test
    @Transactional
    public void createEmulatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emulatorRepository.findAll().size();

        // Create the Emulator with an existing ID
        emulator.setId(1L);
        EmulatorDTO emulatorDTO = emulatorMapper.toDto(emulator);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmulatorMockMvc.perform(post("/api/emulators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emulatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Emulator in the database
        List<Emulator> emulatorList = emulatorRepository.findAll();
        assertThat(emulatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = emulatorRepository.findAll().size();
        // set the field null
        emulator.setName(null);

        // Create the Emulator, which fails.
        EmulatorDTO emulatorDTO = emulatorMapper.toDto(emulator);

        restEmulatorMockMvc.perform(post("/api/emulators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emulatorDTO)))
            .andExpect(status().isBadRequest());

        List<Emulator> emulatorList = emulatorRepository.findAll();
        assertThat(emulatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = emulatorRepository.findAll().size();
        // set the field null
        emulator.setVersion(null);

        // Create the Emulator, which fails.
        EmulatorDTO emulatorDTO = emulatorMapper.toDto(emulator);

        restEmulatorMockMvc.perform(post("/api/emulators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emulatorDTO)))
            .andExpect(status().isBadRequest());

        List<Emulator> emulatorList = emulatorRepository.findAll();
        assertThat(emulatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmulators() throws Exception {
        // Initialize the database
        emulatorRepository.saveAndFlush(emulator);

        // Get all the emulatorList
        restEmulatorMockMvc.perform(get("/api/emulators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emulator.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].pathIcon").value(hasItem(DEFAULT_PATH_ICON.toString())));
    }

    @Test
    @Transactional
    public void getEmulator() throws Exception {
        // Initialize the database
        emulatorRepository.saveAndFlush(emulator);

        // Get the emulator
        restEmulatorMockMvc.perform(get("/api/emulators/{id}", emulator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emulator.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.pathIcon").value(DEFAULT_PATH_ICON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmulator() throws Exception {
        // Get the emulator
        restEmulatorMockMvc.perform(get("/api/emulators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmulator() throws Exception {
        // Initialize the database
        emulatorRepository.saveAndFlush(emulator);
        int databaseSizeBeforeUpdate = emulatorRepository.findAll().size();

        // Update the emulator
        Emulator updatedEmulator = emulatorRepository.findOne(emulator.getId());
        // Disconnect from session so that the updates on updatedEmulator are not directly saved in db
        em.detach(updatedEmulator);
        updatedEmulator
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .pathIcon(UPDATED_PATH_ICON);
        EmulatorDTO emulatorDTO = emulatorMapper.toDto(updatedEmulator);

        restEmulatorMockMvc.perform(put("/api/emulators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emulatorDTO)))
            .andExpect(status().isOk());

        // Validate the Emulator in the database
        List<Emulator> emulatorList = emulatorRepository.findAll();
        assertThat(emulatorList).hasSize(databaseSizeBeforeUpdate);
        Emulator testEmulator = emulatorList.get(emulatorList.size() - 1);
        assertThat(testEmulator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmulator.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testEmulator.getPathIcon()).isEqualTo(UPDATED_PATH_ICON);
    }

    @Test
    @Transactional
    public void updateNonExistingEmulator() throws Exception {
        int databaseSizeBeforeUpdate = emulatorRepository.findAll().size();

        // Create the Emulator
        EmulatorDTO emulatorDTO = emulatorMapper.toDto(emulator);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmulatorMockMvc.perform(put("/api/emulators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emulatorDTO)))
            .andExpect(status().isCreated());

        // Validate the Emulator in the database
        List<Emulator> emulatorList = emulatorRepository.findAll();
        assertThat(emulatorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmulator() throws Exception {
        // Initialize the database
        emulatorRepository.saveAndFlush(emulator);
        int databaseSizeBeforeDelete = emulatorRepository.findAll().size();

        // Get the emulator
        restEmulatorMockMvc.perform(delete("/api/emulators/{id}", emulator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Emulator> emulatorList = emulatorRepository.findAll();
        assertThat(emulatorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emulator.class);
        Emulator emulator1 = new Emulator();
        emulator1.setId(1L);
        Emulator emulator2 = new Emulator();
        emulator2.setId(emulator1.getId());
        assertThat(emulator1).isEqualTo(emulator2);
        emulator2.setId(2L);
        assertThat(emulator1).isNotEqualTo(emulator2);
        emulator1.setId(null);
        assertThat(emulator1).isNotEqualTo(emulator2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmulatorDTO.class);
        EmulatorDTO emulatorDTO1 = new EmulatorDTO();
        emulatorDTO1.setId(1L);
        EmulatorDTO emulatorDTO2 = new EmulatorDTO();
        assertThat(emulatorDTO1).isNotEqualTo(emulatorDTO2);
        emulatorDTO2.setId(emulatorDTO1.getId());
        assertThat(emulatorDTO1).isEqualTo(emulatorDTO2);
        emulatorDTO2.setId(2L);
        assertThat(emulatorDTO1).isNotEqualTo(emulatorDTO2);
        emulatorDTO1.setId(null);
        assertThat(emulatorDTO1).isNotEqualTo(emulatorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emulatorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emulatorMapper.fromId(null)).isNull();
    }
}
