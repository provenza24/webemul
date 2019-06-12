package fr.provenzano.webemul.service;

import fr.provenzano.webemul.service.dto.ParameterDTO;
import java.util.List;

/**
 * Service Interface for managing Parameter.
 */
public interface ParameterService {

    /**
     * Save a parameter.
     *
     * @param parameterDTO the entity to save
     * @return the persisted entity
     */
    ParameterDTO save(ParameterDTO parameterDTO);

    /**
     * Get all the parameters.
     *
     * @return the list of entities
     */
    List<ParameterDTO> findAll();

    /**
     * Get the "id" parameter.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ParameterDTO findOne(Long id);

    /**
     * Delete the "id" parameter.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
