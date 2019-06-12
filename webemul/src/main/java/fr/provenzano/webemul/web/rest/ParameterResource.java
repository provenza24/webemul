package fr.provenzano.webemul.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import fr.provenzano.webemul.service.TechParameterService;
import fr.provenzano.webemul.service.dto.ParameterDTO;
import fr.provenzano.webemul.service.impl.ProxyManager;
import fr.provenzano.webemul.web.rest.errors.BadRequestAlertException;
import fr.provenzano.webemul.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Parameter.
 */
@RestController
@RequestMapping("/api")
public class ParameterResource {

    private final Logger log = LoggerFactory.getLogger(ParameterResource.class);

    private static final String ENTITY_NAME = "parameter";

    private final TechParameterService parameterService;
    
    private final ProxyManager proxyManager;

    public ParameterResource(TechParameterService parameterService, ProxyManager proxyManager) {
        this.parameterService = parameterService;
        this.proxyManager = proxyManager;
    }

    /**
     * POST  /parameters : Create a new parameter.
     *
     * @param parameterDTO the parameterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parameterDTO, or with status 400 (Bad Request) if the parameter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parameters")
    @Timed
    public ResponseEntity<ParameterDTO> createParameter(@Valid @RequestBody ParameterDTO parameterDTO) throws URISyntaxException {
        log.debug("REST request to save Parameter : {}", parameterDTO);
        if (parameterDTO.getId() != null) {
            throw new BadRequestAlertException("A new parameter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParameterDTO result = parameterService.save(parameterDTO);
        return ResponseEntity.created(new URI("/api/parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getName()))
            .body(result);
    }

    /**
     * PUT  /parameters : Updates an existing parameter.
     *
     * @param parameterDTO the parameterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parameterDTO,
     * or with status 400 (Bad Request) if the parameterDTO is not valid,
     * or with status 500 (Internal Server Error) if the parameterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */    
    @PutMapping("/parameters")
    @Timed    
    public ResponseEntity<ParameterDTO> updateParameter(@Valid @RequestBody ParameterDTO parameterDTO) throws URISyntaxException {
        log.debug("REST request to update Parameter : {}", parameterDTO);
        if (parameterDTO.getId() == null) {
            return createParameter(parameterDTO);
        }
        ParameterDTO result = parameterService.save(parameterDTO);       
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parameterDTO.getName()))
            .body(result);
    }

    /**
     * GET  /parameters : get all the parameters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of parameters in body
     */
    @GetMapping("/parameters")
    @Timed
    public List<ParameterDTO> getAllParameters() {
        log.debug("REST request to get all Parameters");
        return parameterService.findAll();
        }

    /**
     * GET  /parameters/:id : get the "id" parameter.
     *
     * @param id the id of the parameterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parameterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/parameters/{id}")
    @Timed
    public ResponseEntity<ParameterDTO> getParameter(@PathVariable Long id) {
        log.debug("REST request to get Parameter : {}", id);
        ParameterDTO parameterDTO = parameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parameterDTO));
    }

    /**
     * DELETE  /parameters/:id : delete the "id" parameter.
     *
     * @param id the id of the parameterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parameters/{id}")
    @Timed    
    public ResponseEntity<Void> deleteParameter(@PathVariable Long id) {
        log.debug("REST request to delete Parameter : {}", id);
        parameterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
