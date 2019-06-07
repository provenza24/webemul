package fr.provenzano.webemul.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;

import fr.provenzano.webemul.domain.Rom;
import fr.provenzano.webemul.service.dto.RomDTO;

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
     * Get all the roms.
     * 
     * @param pageable the pagination information
     * @param specifications the specifications information
     * @return the list of entities
     */
    Page<RomDTO> findAll(Pageable pageable, Specifications<Rom> specifications);

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
