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
import fr.provenzano.webemul.domain.Rom;
import fr.provenzano.webemul.repository.RomRepository;
import fr.provenzano.webemul.service.RomService;
import fr.provenzano.webemul.service.dto.RomDTO;
import fr.provenzano.webemul.service.mapper.RomMapper;
import fr.provenzano.webemul.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the RomResource REST controller.
 *
 * @see RomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebEmul.class)
public class RomResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH_FILE = "AAAAAAAAAA";
    private static final String UPDATED_PATH_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final String DEFAULT_PATH_COVER = "AAAAAAAAAA";
    private static final String UPDATED_PATH_COVER = "BBBBBBBBBB";

    @Autowired
    private RomRepository romRepository;

    @Autowired
    private RomMapper romMapper;

    @Autowired
    private RomService romService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRomMockMvc;

    private Rom rom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RomResource romResource = new RomResource(romService);
        this.restRomMockMvc = MockMvcBuilders.standaloneSetup(romResource)
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
    public static Rom createEntity(EntityManager em) {
        Rom rom = new Rom()
            .name(DEFAULT_NAME)
            .pathFile(DEFAULT_PATH_FILE)
            .extension(DEFAULT_EXTENSION)
            .pathCover(DEFAULT_PATH_COVER);
        return rom;
    }

    @Before
    public void initTest() {
        rom = createEntity(em);
    }

    @Test
    @Transactional
    public void createRom() throws Exception {
        int databaseSizeBeforeCreate = romRepository.findAll().size();

        // Create the Rom
        RomDTO romDTO = romMapper.toDto(rom);
        restRomMockMvc.perform(post("/api/roms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(romDTO)))
            .andExpect(status().isCreated());

        // Validate the Rom in the database
        List<Rom> romList = romRepository.findAll();
        assertThat(romList).hasSize(databaseSizeBeforeCreate + 1);
        Rom testRom = romList.get(romList.size() - 1);
        assertThat(testRom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRom.getPathFile()).isEqualTo(DEFAULT_PATH_FILE);
        assertThat(testRom.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testRom.getPathCover()).isEqualTo(DEFAULT_PATH_COVER);
    }

    @Test
    @Transactional
    public void createRomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = romRepository.findAll().size();

        // Create the Rom with an existing ID
        rom.setId(1L);
        RomDTO romDTO = romMapper.toDto(rom);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRomMockMvc.perform(post("/api/roms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(romDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rom in the database
        List<Rom> romList = romRepository.findAll();
        assertThat(romList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = romRepository.findAll().size();
        // set the field null
        rom.setName(null);

        // Create the Rom, which fails.
        RomDTO romDTO = romMapper.toDto(rom);

        restRomMockMvc.perform(post("/api/roms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(romDTO)))
            .andExpect(status().isBadRequest());

        List<Rom> romList = romRepository.findAll();
        assertThat(romList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = romRepository.findAll().size();
        // set the field null
        rom.setPathFile(null);

        // Create the Rom, which fails.
        RomDTO romDTO = romMapper.toDto(rom);

        restRomMockMvc.perform(post("/api/roms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(romDTO)))
            .andExpect(status().isBadRequest());

        List<Rom> romList = romRepository.findAll();
        assertThat(romList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoms() throws Exception {
        // Initialize the database
        romRepository.saveAndFlush(rom);

        // Get all the romList
        restRomMockMvc.perform(get("/api/roms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rom.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].pathFile").value(hasItem(DEFAULT_PATH_FILE.toString())))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION.toString())))
            .andExpect(jsonPath("$.[*].pathCover").value(hasItem(DEFAULT_PATH_COVER.toString())));
    }

    @Test
    @Transactional
    public void getRom() throws Exception {
        // Initialize the database
        romRepository.saveAndFlush(rom);

        // Get the rom
        restRomMockMvc.perform(get("/api/roms/{id}", rom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rom.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.pathFile").value(DEFAULT_PATH_FILE.toString()))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION.toString()))
            .andExpect(jsonPath("$.pathCover").value(DEFAULT_PATH_COVER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRom() throws Exception {
        // Get the rom
        restRomMockMvc.perform(get("/api/roms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRom() throws Exception {
        // Initialize the database
        romRepository.saveAndFlush(rom);
        int databaseSizeBeforeUpdate = romRepository.findAll().size();

        // Update the rom
        Rom updatedRom = romRepository.findOne(rom.getId());
        // Disconnect from session so that the updates on updatedRom are not directly saved in db
        em.detach(updatedRom);
        updatedRom
            .name(UPDATED_NAME)
            .pathFile(UPDATED_PATH_FILE)
            .extension(UPDATED_EXTENSION)
            .pathCover(UPDATED_PATH_COVER);
        RomDTO romDTO = romMapper.toDto(updatedRom);

        restRomMockMvc.perform(put("/api/roms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(romDTO)))
            .andExpect(status().isOk());

        // Validate the Rom in the database
        List<Rom> romList = romRepository.findAll();
        assertThat(romList).hasSize(databaseSizeBeforeUpdate);
        Rom testRom = romList.get(romList.size() - 1);
        assertThat(testRom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRom.getPathFile()).isEqualTo(UPDATED_PATH_FILE);
        assertThat(testRom.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testRom.getPathCover()).isEqualTo(UPDATED_PATH_COVER);
    }

    @Test
    @Transactional
    public void updateNonExistingRom() throws Exception {
        int databaseSizeBeforeUpdate = romRepository.findAll().size();

        // Create the Rom
        RomDTO romDTO = romMapper.toDto(rom);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRomMockMvc.perform(put("/api/roms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(romDTO)))
            .andExpect(status().isCreated());

        // Validate the Rom in the database
        List<Rom> romList = romRepository.findAll();
        assertThat(romList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRom() throws Exception {
        // Initialize the database
        romRepository.saveAndFlush(rom);
        int databaseSizeBeforeDelete = romRepository.findAll().size();

        // Get the rom
        restRomMockMvc.perform(delete("/api/roms/{id}", rom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rom> romList = romRepository.findAll();
        assertThat(romList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rom.class);
        Rom rom1 = new Rom();
        rom1.setId(1L);
        Rom rom2 = new Rom();
        rom2.setId(rom1.getId());
        assertThat(rom1).isEqualTo(rom2);
        rom2.setId(2L);
        assertThat(rom1).isNotEqualTo(rom2);
        rom1.setId(null);
        assertThat(rom1).isNotEqualTo(rom2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RomDTO.class);
        RomDTO romDTO1 = new RomDTO();
        romDTO1.setId(1L);
        RomDTO romDTO2 = new RomDTO();
        assertThat(romDTO1).isNotEqualTo(romDTO2);
        romDTO2.setId(romDTO1.getId());
        assertThat(romDTO1).isEqualTo(romDTO2);
        romDTO2.setId(2L);
        assertThat(romDTO1).isNotEqualTo(romDTO2);
        romDTO1.setId(null);
        assertThat(romDTO1).isNotEqualTo(romDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(romMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(romMapper.fromId(null)).isNull();
    }
}
