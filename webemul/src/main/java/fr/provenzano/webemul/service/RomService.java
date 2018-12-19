package fr.provenzano.webemul.service;

import fr.provenzano.webemul.service.dto.RomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Rom.
 */
public interface RomService {

    /**
     * Save a rom.
     *
     * @param romDTO the entity to save
     * @return the persisted entity
     */
    RomDTO save(RomDTO romDTO);

    /**
     * Get all the roms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RomDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rom.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RomDTO findOne(Long id);

    /**
     * Delete the "id" rom.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
