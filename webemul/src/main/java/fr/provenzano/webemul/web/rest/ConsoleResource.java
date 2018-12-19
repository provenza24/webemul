package fr.provenzano.webemul.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.provenzano.webemul.service.ConsoleService;
import fr.provenzano.webemul.web.rest.errors.BadRequestAlertException;
import fr.provenzano.webemul.web.rest.util.HeaderUtil;
import fr.provenzano.webemul.service.dto.ConsoleDTO;
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
 * REST controller for managing Console.
 */
@RestController
@RequestMapping("/api")
public class ConsoleResource {

    private final Logger log = LoggerFactory.getLogger(ConsoleResource.class);

    private static final String ENTITY_NAME = "console";

    private final ConsoleService consoleService;

    public ConsoleResource(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    /**
     * POST  /consoles : Create a new console.
     *
     * @param consoleDTO the consoleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consoleDTO, or with status 400 (Bad Request) if the console has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consoles")
    @Timed
    public ResponseEntity<ConsoleDTO> createConsole(@Valid @RequestBody ConsoleDTO consoleDTO) throws URISyntaxException {
        log.debug("REST request to save Console : {}", consoleDTO);
        if (consoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new console cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsoleDTO result = consoleService.save(consoleDTO);
        return ResponseEntity.created(new URI("/api/consoles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consoles : Updates an existing console.
     *
     * @param consoleDTO the consoleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consoleDTO,
     * or with status 400 (Bad Request) if the consoleDTO is not valid,
     * or with status 500 (Internal Server Error) if the consoleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consoles")
    @Timed
    public ResponseEntity<ConsoleDTO> updateConsole(@Valid @RequestBody ConsoleDTO consoleDTO) throws URISyntaxException {
        log.debug("REST request to update Console : {}", consoleDTO);
        if (consoleDTO.getId() == null) {
            return createConsole(consoleDTO);
        }
        ConsoleDTO result = consoleService.save(consoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consoles : get all the consoles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of consoles in body
     */
    @GetMapping("/consoles")
    @Timed
    public List<ConsoleDTO> getAllConsoles() {
        log.debug("REST request to get all Consoles");
        return consoleService.findAll();
        }

    /**
     * GET  /consoles/:id : get the "id" console.
     *
     * @param id the id of the consoleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consoleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/consoles/{id}")
    @Timed
    public ResponseEntity<ConsoleDTO> getConsole(@PathVariable Long id) {
        log.debug("REST request to get Console : {}", id);
        ConsoleDTO consoleDTO = consoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consoleDTO));
    }

    /**
     * DELETE  /consoles/:id : delete the "id" console.
     *
     * @param id the id of the consoleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consoles/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsole(@PathVariable Long id) {
        log.debug("REST request to delete Console : {}", id);
        consoleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
