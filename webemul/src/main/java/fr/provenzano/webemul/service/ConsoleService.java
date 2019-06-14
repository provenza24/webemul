package fr.provenzano.webemul.service;

import java.util.List;

import fr.provenzano.webemul.domain.Rom;
import fr.provenzano.webemul.service.dto.ConsoleDTO;

/**
 * Service Interface for managing Console.
 */
public interface ConsoleService {

    /**
     * Save a console.
     *
     * @param consoleDTO the entity to save
     * @return the persisted entity
     */
    ConsoleDTO save(ConsoleDTO consoleDTO);

    /**
     * Get all the consoles.
     *
     * @return the list of entities
     */
    List<ConsoleDTO> findAll();

    /**
     * Get the "id" console.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConsoleDTO findOne(Long id);

    /**
     * Delete the "id" console.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    List<Rom> findConsoleRoms(Long consoleId);
}
