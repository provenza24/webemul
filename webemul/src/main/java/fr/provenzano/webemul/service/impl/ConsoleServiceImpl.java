package fr.provenzano.webemul.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.provenzano.webemul.domain.Console;
import fr.provenzano.webemul.domain.Rom;
import fr.provenzano.webemul.repository.ConsoleRepository;
import fr.provenzano.webemul.repository.RomRepository;
import fr.provenzano.webemul.service.ConsoleService;
import fr.provenzano.webemul.service.dto.ConsoleDTO;
import fr.provenzano.webemul.service.mapper.ConsoleMapper;

/**
 * Service Implementation for managing Console.
 */
@Service
@Transactional
public class ConsoleServiceImpl implements ConsoleService {

    private final Logger log = LoggerFactory.getLogger(ConsoleServiceImpl.class);

    private final ConsoleRepository consoleRepository;

    private final ConsoleMapper consoleMapper;
    
    private final RomRepository romRepository;

    public ConsoleServiceImpl(ConsoleRepository consoleRepository, ConsoleMapper consoleMapper, RomRepository romRepository) {
        this.consoleRepository = consoleRepository;
        this.consoleMapper = consoleMapper;
        this.romRepository = romRepository;
    }

    /**
     * Save a console.
     *
     * @param consoleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsoleDTO save(ConsoleDTO consoleDTO) {
        log.debug("Request to save Console : {}", consoleDTO);
        Console console = consoleMapper.toEntity(consoleDTO);
        console = consoleRepository.save(console);
        return consoleMapper.toDto(console);
    }

    /**
     * Get all the consoles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConsoleDTO> findAll() {
        log.debug("Request to get all Consoles");
        return consoleRepository.findAllWithEagerRelationships().stream()
            .map(consoleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one console by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConsoleDTO findOne(Long id) {
        log.debug("Request to get Console : {}", id);
        Console console = consoleRepository.findOneWithEagerRelationships(id);
        return consoleMapper.toDto(console);
    }

    /**
     * Delete the console by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Console : {}", id);
        consoleRepository.delete(id);
    }
    
    public List<Rom> findConsoleRoms(Long consoleId) {
    	return romRepository.findConsoleRoms(consoleId);
    }
}
