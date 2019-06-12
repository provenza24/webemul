package fr.provenzano.webemul.service.mapper;

import org.mapstruct.Mapper;

import fr.provenzano.webemul.domain.Parameter;
import fr.provenzano.webemul.service.dto.ParameterDTO;

/**
 * Mapper for the entity Parameter and its DTO ParameterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TechParameterMapper extends EntityMapper<ParameterDTO, Parameter> {



    default Parameter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parameter parameter = new Parameter();
        parameter.setId(id);
        return parameter;
    }
}
