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
import fr.provenzano.webemul.domain.Console;
import fr.provenzano.webemul.repository.ConsoleRepository;
import fr.provenzano.webemul.service.ConsoleService;
import fr.provenzano.webemul.service.dto.ConsoleDTO;
import fr.provenzano.webemul.service.mapper.ConsoleMapper;
import fr.provenzano.webemul.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ConsoleResource REST controller.
 *
 * @see ConsoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebEmul.class)
public class ConsoleResourceIntTest {

    private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH_ICON = "AAAAAAAAAA";
    private static final String UPDATED_PATH_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final Integer DEFAULT_GENERATION = 1;
    private static final Integer UPDATED_GENERATION = 2;

    private static final Integer DEFAULT_BITS = 1;
    private static final Integer UPDATED_BITS = 2;

    private static final String DEFAULT_RESUME = "AAAAAAAAAA";
    private static final String UPDATED_RESUME = "BBBBBBBBBB";

    @Autowired
    private ConsoleRepository consoleRepository;

    @Autowired
    private ConsoleMapper consoleMapper;

    @Autowired
    private ConsoleService consoleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsoleMockMvc;

    private Console console;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsoleResource consoleResource = new ConsoleResource(consoleService);
        this.restConsoleMockMvc = MockMvcBuilders.standaloneSetup(consoleResource)
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
    public static Console createEntity(EntityManager em) {
        Console console = new Console()
            .abbreviation(DEFAULT_ABBREVIATION)
            .name(DEFAULT_NAME)
            .pathIcon(DEFAULT_PATH_ICON)
            .manufacturer(DEFAULT_MANUFACTURER)
            .generation(DEFAULT_GENERATION)
            .bits(DEFAULT_BITS)
            .resume(DEFAULT_RESUME);
        return console;
    }

    @Before
    public void initTest() {
        console = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsole() throws Exception {
        int databaseSizeBeforeCreate = consoleRepository.findAll().size();

        // Create the Console
        ConsoleDTO consoleDTO = consoleMapper.toDto(console);
        restConsoleMockMvc.perform(post("/api/consoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consoleDTO)))
            .andExpect(status().isCreated());

        // Validate the Console in the database
        List<Console> consoleList = consoleRepository.findAll();
        assertThat(consoleList).hasSize(databaseSizeBeforeCreate + 1);
        Console testConsole = consoleList.get(consoleList.size() - 1);
        assertThat(testConsole.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
        assertThat(testConsole.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConsole.getPathIcon()).isEqualTo(DEFAULT_PATH_ICON);
        assertThat(testConsole.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testConsole.getGeneration()).isEqualTo(DEFAULT_GENERATION);
        assertThat(testConsole.getBits()).isEqualTo(DEFAULT_BITS);
        assertThat(testConsole.getResume()).isEqualTo(DEFAULT_RESUME);
    }

    @Test
    @Transactional
    public void createConsoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consoleRepository.findAll().size();

        // Create the Console with an existing ID
        console.setId(1L);
        ConsoleDTO consoleDTO = consoleMapper.toDto(console);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsoleMockMvc.perform(post("/api/consoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Console in the database
        List<Console> consoleList = consoleRepository.findAll();
        assertThat(consoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAbbreviationIsRequired() throws Exception {
        int databaseSizeBeforeTest = consoleRepository.findAll().size();
        // set the field null
        console.setAbbreviation(null);

        // Create the Console, which fails.
        ConsoleDTO consoleDTO = consoleMapper.toDto(console);

        restConsoleMockMvc.perform(post("/api/consoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consoleDTO)))
            .andExpect(status().isBadRequest());

        List<Console> consoleList = consoleRepository.findAll();
        assertThat(consoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = consoleRepository.findAll().size();
        // set the field null
        console.setName(null);

        // Create the Console, which fails.
        ConsoleDTO consoleDTO = consoleMapper.toDto(console);

        restConsoleMockMvc.perform(post("/api/consoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consoleDTO)))
            .andExpect(status().isBadRequest());

        List<Console> consoleList = consoleRepository.findAll();
        assertThat(consoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsoles() throws Exception {
        // Initialize the database
        consoleRepository.saveAndFlush(console);

        // Get all the consoleList
        restConsoleMockMvc.perform(get("/api/consoles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(console.getId().intValue())))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].pathIcon").value(hasItem(DEFAULT_PATH_ICON.toString())))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER.toString())))
            .andExpect(jsonPath("$.[*].generation").value(hasItem(DEFAULT_GENERATION)))
            .andExpect(jsonPath("$.[*].bits").value(hasItem(DEFAULT_BITS)))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME.toString())));
    }

    @Test
    @Transactional
    public void getConsole() throws Exception {
        // Initialize the database
        consoleRepository.saveAndFlush(console);

        // Get the console
        restConsoleMockMvc.perform(get("/api/consoles/{id}", console.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(console.getId().intValue()))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.pathIcon").value(DEFAULT_PATH_ICON.toString()))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER.toString()))
            .andExpect(jsonPath("$.generation").value(DEFAULT_GENERATION))
            .andExpect(jsonPath("$.bits").value(DEFAULT_BITS))
            .andExpect(jsonPath("$.resume").value(DEFAULT_RESUME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsole() throws Exception {
        // Get the console
        restConsoleMockMvc.perform(get("/api/consoles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsole() throws Exception {
        // Initialize the database
        consoleRepository.saveAndFlush(console);
        int databaseSizeBeforeUpdate = consoleRepository.findAll().size();

        // Update the console
        Console updatedConsole = consoleRepository.findOne(console.getId());
        // Disconnect from session so that the updates on updatedConsole are not directly saved in db
        em.detach(updatedConsole);
        updatedConsole
            .abbreviation(UPDATED_ABBREVIATION)
            .name(UPDATED_NAME)
            .pathIcon(UPDATED_PATH_ICON)
            .manufacturer(UPDATED_MANUFACTURER)
            .generation(UPDATED_GENERATION)
            .bits(UPDATED_BITS)
            .resume(UPDATED_RESUME);
        ConsoleDTO consoleDTO = consoleMapper.toDto(updatedConsole);

        restConsoleMockMvc.perform(put("/api/consoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consoleDTO)))
            .andExpect(status().isOk());

        // Validate the Console in the database
        List<Console> consoleList = consoleRepository.findAll();
        assertThat(consoleList).hasSize(databaseSizeBeforeUpdate);
        Console testConsole = consoleList.get(consoleList.size() - 1);
        assertThat(testConsole.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
        assertThat(testConsole.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConsole.getPathIcon()).isEqualTo(UPDATED_PATH_ICON);
        assertThat(testConsole.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testConsole.getGeneration()).isEqualTo(UPDATED_GENERATION);
        assertThat(testConsole.getBits()).isEqualTo(UPDATED_BITS);
        assertThat(testConsole.getResume()).isEqualTo(UPDATED_RESUME);
    }

    @Test
    @Transactional
    public void updateNonExistingConsole() throws Exception {
        int databaseSizeBeforeUpdate = consoleRepository.findAll().size();

        // Create the Console
        ConsoleDTO consoleDTO = consoleMapper.toDto(console);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsoleMockMvc.perform(put("/api/consoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consoleDTO)))
            .andExpect(status().isCreated());

        // Validate the Console in the database
        List<Console> consoleList = consoleRepository.findAll();
        assertThat(consoleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsole() throws Exception {
        // Initialize the database
        consoleRepository.saveAndFlush(console);
        int databaseSizeBeforeDelete = consoleRepository.findAll().size();

        // Get the console
        restConsoleMockMvc.perform(delete("/api/consoles/{id}", console.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Console> consoleList = consoleRepository.findAll();
        assertThat(consoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Console.class);
        Console console1 = new Console();
        console1.setId(1L);
        Console console2 = new Console();
        console2.setId(console1.getId());
        assertThat(console1).isEqualTo(console2);
        console2.setId(2L);
        assertThat(console1).isNotEqualTo(console2);
        console1.setId(null);
        assertThat(console1).isNotEqualTo(console2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsoleDTO.class);
        ConsoleDTO consoleDTO1 = new ConsoleDTO();
        consoleDTO1.setId(1L);
        ConsoleDTO consoleDTO2 = new ConsoleDTO();
        assertThat(consoleDTO1).isNotEqualTo(consoleDTO2);
        consoleDTO2.setId(consoleDTO1.getId());
        assertThat(consoleDTO1).isEqualTo(consoleDTO2);
        consoleDTO2.setId(2L);
        assertThat(consoleDTO1).isNotEqualTo(consoleDTO2);
        consoleDTO1.setId(null);
        assertThat(consoleDTO1).isNotEqualTo(consoleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consoleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consoleMapper.fromId(null)).isNull();
    }
}
