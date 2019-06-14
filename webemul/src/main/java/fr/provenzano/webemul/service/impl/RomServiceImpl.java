package fr.provenzano.webemul.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.provenzano.webemul.domain.Rom;
import fr.provenzano.webemul.repository.RomRepository;
import fr.provenzano.webemul.service.RomService;
import fr.provenzano.webemul.service.dto.RomDTO;
import fr.provenzano.webemul.service.mapper.RomMapper;


/**
 * Service Implementation for managing Rom.
 */
@Service
@Transactional
public class RomServiceImpl implements RomService {

    private final Logger log = LoggerFactory.getLogger(RomServiceImpl.class);

    private final RomRepository romRepository;

    private final RomMapper romMapper;

    public RomServiceImpl(RomRepository romRepository, RomMapper romMapper) {
        this.romRepository = romRepository;
        this.romMapper = romMapper;
    }

    /**
     * Save a rom.
     *
     * @param romDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RomDTO save(RomDTO romDTO) {
        log.debug("Request to save Rom : {}", romDTO);
        Rom rom = romMapper.toEntity(romDTO);
        rom = romRepository.save(rom);
        return romMapper.toDto(rom);
    }

    /**
     * Get all the roms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Roms");
        return romRepository.findAll(pageable)
            .map(romMapper::toDto);
    }
    
    /**
     * Get all the roms.
     *
     * @param pageable the pagination information
     * @param specifications Specifications associated to the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RomDTO> findAll(Pageable pageable, Specifications<Rom> specifications) {
        log.debug("Request to get all Roms");
        return romRepository.findAll(specifications, pageable)
            .map(romMapper::toDto);
    }

    /**
     * Get one rom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RomDTO findOne(Long id) {
        log.debug("Request to get Rom : {}", id);
        Rom rom = romRepository.findOneWithEagerRelationships(id);
        return romMapper.toDto(rom);
    }

    /**
     * Delete the rom by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rom : {}", id);
        romRepository.delete(id);
    }
    
    /**
     * Save a rom.
     *
     * @param romDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public boolean saveByFilePath(RomDTO romDTO) {
        log.debug("Request to save Rom : {}", romDTO);
        Rom dbRom = romRepository.findByPathFile(romDTO.getPathFile());
        if (dbRom==null) {
        	dbRom = romMapper.toEntity(romDTO);
        	dbRom = romRepository.save(dbRom);
        	return true;
        }        
        return false;
    }
    
}
