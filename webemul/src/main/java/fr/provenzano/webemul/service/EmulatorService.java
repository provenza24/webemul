package fr.provenzano.webemul.service;

import fr.provenzano.webemul.service.dto.EmulatorDTO;
import java.util.List;

/**
 * Service Interface for managing Emulator.
 */
public interface EmulatorService {

    /**
     * Save a emulator.
     *
     * @param emulatorDTO the entity to save
     * @return the persisted entity
     */
    EmulatorDTO save(EmulatorDTO emulatorDTO);

    /**
     * Get all the emulators.
     *
     * @return the list of entities
     */
    List<EmulatorDTO> findAll();

    /**
     * Get the "id" emulator.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EmulatorDTO findOne(Long id);

    /**
     * Delete the "id" emulator.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
