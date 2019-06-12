package fr.provenzano.webemul.service.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

import fr.provenzano.webemul.domain.Parameter;
import fr.provenzano.webemul.service.dto.ParameterDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-12-26T11:36:09+0100",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class TechParameterMapperImpl implements TechParameterMapper {

    @Override
    public Parameter toEntity(ParameterDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Parameter parameter = new Parameter();

        parameter.setId( dto.getId() );
        parameter.setName( dto.getName() );
        parameter.setLabel( dto.getLabel() );
        parameter.setValue( dto.getValue() );
        parameter.setType( dto.getType() );

        return parameter;
    }

    @Override
    public ParameterDTO toDto(Parameter entity) {
        if ( entity == null ) {
            return null;
        }

        ParameterDTO parameterDTO = new ParameterDTO();

        parameterDTO.setId( entity.getId() );
        parameterDTO.setName( entity.getName() );
        parameterDTO.setLabel( entity.getLabel() );
        parameterDTO.setValue( entity.getValue() );
        parameterDTO.setType( entity.getType() );

        return parameterDTO;
    }

    @Override
    public List<Parameter> toEntity(List<ParameterDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Parameter> list = new ArrayList<Parameter>( dtoList.size() );
        for ( ParameterDTO parameterDTO : dtoList ) {
            list.add( toEntity( parameterDTO ) );
        }

        return list;
    }

    @Override
    public List<ParameterDTO> toDto(List<Parameter> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ParameterDTO> list = new ArrayList<ParameterDTO>( entityList.size() );
        for ( Parameter parameter : entityList ) {
            list.add( toDto( parameter ) );
        }

        return list;
    }
}
