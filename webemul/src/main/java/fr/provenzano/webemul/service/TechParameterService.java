package fr.provenzano.webemul.service;


import java.util.List;

import fr.provenzano.webemul.service.dto.ParameterDTO;

/**
 * Service Interface for managing Parameter.
 */
public interface TechParameterService {
	
	public static final String PARAMETER_ON = "ON";
	
	public static final String PARAMETER_OFF = "OFF";
	
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
    
    /**
     * Find a parameter by name
     * @param name the name of the parameter
     * @return the entity
     */
    ParameterDTO findByName(String name);
    
    /**
     * Check if parameter exists and value is "ON"
     * @param name
     * @return
     */
    Boolean isParameterON(String name);
    
    /**
     * Retrieve integer value of parameter
     * @param name
     * @return
     */
    Integer getIntegerValue(String name);
    
    /**
     * Retrieve String parameter value
     * @param name
     * @return
     */
    String getStringValue(String name);
}
