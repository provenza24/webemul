package fr.provenzano.webemul.service.impl;

import fr.provenzano.webemul.service.EmulatorService;
import fr.provenzano.webemul.domain.Emulator;
import fr.provenzano.webemul.repository.EmulatorRepository;
import fr.provenzano.webemul.service.dto.EmulatorDTO;
import fr.provenzano.webemul.service.mapper.EmulatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Emulator.
 */
@Service
@Transactional
public class EmulatorServiceImpl implements EmulatorService {

    private final Logger log = LoggerFactory.getLogger(EmulatorServiceImpl.class);

    private final EmulatorRepository emulatorRepository;

    private final EmulatorMapper emulatorMapper;

    public EmulatorServiceImpl(EmulatorRepository emulatorRepository, EmulatorMapper emulatorMapper) {
        this.emulatorRepository = emulatorRepository;
        this.emulatorMapper = emulatorMapper;
    }

    /**
     * Save a emulator.
     *
     * @param emulatorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmulatorDTO save(EmulatorDTO emulatorDTO) {
        log.debug("Request to save Emulator : {}", emulatorDTO);
        Emulator emulator = emulatorMapper.toEntity(emulatorDTO);
        emulator = emulatorRepository.save(emulator);
        return emulatorMapper.toDto(emulator);
    }

    /**
     * Get all the emulators.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmulatorDTO> findAll() {
        log.debug("Request to get all Emulators");
        return emulatorRepository.findAll().stream()
            .map(emulatorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one emulator by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmulatorDTO findOne(Long id) {
        log.debug("Request to get Emulator : {}", id);
        Emulator emulator = emulatorRepository.findOne(id);
        return emulatorMapper.toDto(emulator);
    }

    /**
     * Delete the emulator by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Emulator : {}", id);
        emulatorRepository.delete(id);
    }
}
