package fr.provenzano.webemul.service.mapper;

import fr.provenzano.webemul.domain.Emulator;
import fr.provenzano.webemul.service.dto.EmulatorDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-12-18T11:29:30+0100",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class EmulatorMapperImpl implements EmulatorMapper {

    @Override
    public Emulator toEntity(EmulatorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Emulator emulator = new Emulator();

        emulator.setId( dto.getId() );
        emulator.setName( dto.getName() );
        emulator.setVersion( dto.getVersion() );
        emulator.setPathIcon( dto.getPathIcon() );

        return emulator;
    }

    @Override
    public EmulatorDTO toDto(Emulator entity) {
        if ( entity == null ) {
            return null;
        }

        EmulatorDTO emulatorDTO = new EmulatorDTO();

        emulatorDTO.setId( entity.getId() );
        emulatorDTO.setName( entity.getName() );
        emulatorDTO.setVersion( entity.getVersion() );
        emulatorDTO.setPathIcon( entity.getPathIcon() );

        return emulatorDTO;
    }

    @Override
    public List<Emulator> toEntity(List<EmulatorDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Emulator> list = new ArrayList<Emulator>( dtoList.size() );
        for ( EmulatorDTO emulatorDTO : dtoList ) {
            list.add( toEntity( emulatorDTO ) );
        }

        return list;
    }

    @Override
    public List<EmulatorDTO> toDto(List<Emulator> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<EmulatorDTO> list = new ArrayList<EmulatorDTO>( entityList.size() );
        for ( Emulator emulator : entityList ) {
            list.add( toDto( emulator ) );
        }

        return list;
    }
}
