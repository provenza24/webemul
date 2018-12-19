package fr.provenzano.webemul.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.provenzano.webemul.service.RomService;
import fr.provenzano.webemul.web.rest.errors.BadRequestAlertException;
import fr.provenzano.webemul.web.rest.util.HeaderUtil;
import fr.provenzano.webemul.web.rest.util.PaginationUtil;
import fr.provenzano.webemul.service.dto.RomDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Rom.
 */
@RestController
@RequestMapping("/api")
public class RomResource {

    private final Logger log = LoggerFactory.getLogger(RomResource.class);

    private static final String ENTITY_NAME = "rom";

    private final RomService romService;

    public RomResource(RomService romService) {
        this.romService = romService;
    }

    /**
     * POST  /roms : Create a new rom.
     *
     * @param romDTO the romDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new romDTO, or with status 400 (Bad Request) if the rom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/roms")
    @Timed
    public ResponseEntity<RomDTO> createRom(@Valid @RequestBody RomDTO romDTO) throws URISyntaxException {
        log.debug("REST request to save Rom : {}", romDTO);
        if (romDTO.getId() != null) {
            throw new BadRequestAlertException("A new rom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RomDTO result = romService.save(romDTO);
        return ResponseEntity.created(new URI("/api/roms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /roms : Updates an existing rom.
     *
     * @param romDTO the romDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated romDTO,
     * or with status 400 (Bad Request) if the romDTO is not valid,
     * or with status 500 (Internal Server Error) if the romDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/roms")
    @Timed
    public ResponseEntity<RomDTO> updateRom(@Valid @RequestBody RomDTO romDTO) throws URISyntaxException {
        log.debug("REST request to update Rom : {}", romDTO);
        if (romDTO.getId() == null) {
            return createRom(romDTO);
        }
        RomDTO result = romService.save(romDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, romDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /roms : get all the roms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roms in body
     */
    @GetMapping("/roms")
    @Timed
    public ResponseEntity<List<RomDTO>> getAllRoms(Pageable pageable) {
        log.debug("REST request to get a page of Roms");
        Page<RomDTO> page = romService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/roms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /roms/:id : get the "id" rom.
     *
     * @param id the id of the romDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the romDTO, or with status 404 (Not Found)
     */
    @GetMapping("/roms/{id}")
    @Timed
    public ResponseEntity<RomDTO> getRom(@PathVariable Long id) {
        log.debug("REST request to get Rom : {}", id);
        RomDTO romDTO = romService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(romDTO));
    }

    /**
     * DELETE  /roms/:id : delete the "id" rom.
     *
     * @param id the id of the romDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/roms/{id}")
    @Timed
    public ResponseEntity<Void> deleteRom(@PathVariable Long id) {
        log.debug("REST request to delete Rom : {}", id);
        romService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
