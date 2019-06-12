package fr.provenzano.webemul.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.provenzano.webemul.domain.Parameter;
import fr.provenzano.webemul.repository.ParameterRepository;
import fr.provenzano.webemul.service.TechParameterService;
import fr.provenzano.webemul.service.dto.ParameterDTO;
import fr.provenzano.webemul.service.mapper.TechParameterMapper;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

/**
 * Service Implementation for managing Parameter.
 */
@Service
@Transactional
public class TechParameterServiceImpl implements TechParameterService {

    private final Logger log = LoggerFactory.getLogger(TechParameterServiceImpl.class);

    private final ParameterRepository parameterRepository;

    private final TechParameterMapper parameterMapper;

    public TechParameterServiceImpl(ParameterRepository parameterRepository, TechParameterMapper parameterMapper) {
        this.parameterRepository = parameterRepository;
        this.parameterMapper = parameterMapper;
    }

    /**
     * Save a parameter.
     *
     * @param parameterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParameterDTO save(ParameterDTO parameterDTO) {
        log.debug("Request to save Parameter : {}", parameterDTO);
        Parameter parameter = parameterMapper.toEntity(parameterDTO);
        parameter = parameterRepository.save(parameter);
        return parameterMapper.toDto(parameter);
    }

    /**
     * Get all the parameters.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParameterDTO> findAll() {
        log.debug("Request to get all Parameters");
        return parameterRepository.findAll().stream()
            .map(parameterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one parameter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ParameterDTO findOne(Long id) {
        log.debug("Request to get Parameter : {}", id);
        Parameter parameter = parameterRepository.findOne(id);
        return parameterMapper.toDto(parameter);
    }

    /**
     * Delete the parameter by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parameter : {}", id);
        parameterRepository.delete(id);
    }

	@Override
	public ParameterDTO findByName(String name) {
		log.debug("Request to get Parameter : {}", name);
        Parameter parameter = parameterRepository.findByName(name);
        return parameterMapper.toDto(parameter);
	}
	
	@Override
	public Boolean isParameterON(String name) {
		log.debug("Request to get Parameter : {}", name);
        Parameter parameter = parameterRepository.findByName(name);        
		return parameter!=null && PARAMETER_ON.equals(parameter.getValue());        
	}

	@Override
	public Integer getIntegerValue(String name) {
		log.debug("Request to get Parameter : {}", name);
        Parameter parameter = parameterRepository.findByName(name);
        return parameter!=null && StringUtils.isNumeric(parameter.getValue()) ? Integer.parseInt(parameter.getValue()) : null;
	}

	@Override
	public String getStringValue(String name) {
		log.debug("Request to get Parameter : {}", name);
        Parameter parameter = parameterRepository.findByName(name);
        return parameter!=null && StringUtils.isNotBlank(parameter.getValue()) ? parameter.getValue() : null;
	}
}
