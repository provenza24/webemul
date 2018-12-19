package fr.provenzano.webemul.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.provenzano.webemul.service.EmulatorService;
import fr.provenzano.webemul.web.rest.errors.BadRequestAlertException;
import fr.provenzano.webemul.web.rest.util.HeaderUtil;
import fr.provenzano.webemul.service.dto.EmulatorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Emulator.
 */
@RestController
@RequestMapping("/api")
public class EmulatorResource {

    private final Logger log = LoggerFactory.getLogger(EmulatorResource.class);

    private static final String ENTITY_NAME = "emulator";

    private final EmulatorService emulatorService;

    public EmulatorResource(EmulatorService emulatorService) {
        this.emulatorService = emulatorService;
    }

    /**
     * POST  /emulators : Create a new emulator.
     *
     * @param emulatorDTO the emulatorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emulatorDTO, or with status 400 (Bad Request) if the emulator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/emulators")
    @Timed
    public ResponseEntity<EmulatorDTO> createEmulator(@Valid @RequestBody EmulatorDTO emulatorDTO) throws URISyntaxException {
        log.debug("REST request to save Emulator : {}", emulatorDTO);
        if (emulatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new emulator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmulatorDTO result = emulatorService.save(emulatorDTO);
        return ResponseEntity.created(new URI("/api/emulators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emulators : Updates an existing emulator.
     *
     * @param emulatorDTO the emulatorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emulatorDTO,
     * or with status 400 (Bad Request) if the emulatorDTO is not valid,
     * or with status 500 (Internal Server Error) if the emulatorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/emulators")
    @Timed
    public ResponseEntity<EmulatorDTO> updateEmulator(@Valid @RequestBody EmulatorDTO emulatorDTO) throws URISyntaxException {
        log.debug("REST request to update Emulator : {}", emulatorDTO);
        if (emulatorDTO.getId() == null) {
            return createEmulator(emulatorDTO);
        }
        EmulatorDTO result = emulatorService.save(emulatorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emulatorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emulators : get all the emulators.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of emulators in body
     */
    @GetMapping("/emulators")
    @Timed
    public List<EmulatorDTO> getAllEmulators() {
        log.debug("REST request to get all Emulators");
        List<EmulatorDTO> emulators = emulatorService.findAll();
        return emulators;
        }

    /**
     * GET  /emulators/:id : get the "id" emulator.
     *
     * @param id the id of the emulatorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emulatorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/emulators/{id}")
    @Timed
    public ResponseEntity<EmulatorDTO> getEmulator(@PathVariable Long id) {
        log.debug("REST request to get Emulator : {}", id);
        EmulatorDTO emulatorDTO = emulatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(emulatorDTO));
    }

    /**
     * DELETE  /emulators/:id : delete the "id" emulator.
     *
     * @param id the id of the emulatorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emulators/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmulator(@PathVariable Long id) {
        log.debug("REST request to delete Emulator : {}", id);
        emulatorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
