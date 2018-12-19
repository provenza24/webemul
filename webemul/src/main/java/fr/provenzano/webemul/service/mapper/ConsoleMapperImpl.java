package fr.provenzano.webemul.service.mapper;

import fr.provenzano.webemul.domain.Console;
import fr.provenzano.webemul.domain.Emulator;
import fr.provenzano.webemul.service.dto.ConsoleDTO;
import fr.provenzano.webemul.service.dto.EmulatorDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-12-18T11:29:29+0100",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class ConsoleMapperImpl implements ConsoleMapper {

    @Autowired
    private EmulatorMapper emulatorMapper;

    @Override
    public List<Console> toEntity(List<ConsoleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Console> list = new ArrayList<Console>( dtoList.size() );
        for ( ConsoleDTO consoleDTO : dtoList ) {
            list.add( toEntity( consoleDTO ) );
        }

        return list;
    }

    @Override
    public List<ConsoleDTO> toDto(List<Console> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ConsoleDTO> list = new ArrayList<ConsoleDTO>( entityList.size() );
        for ( Console console : entityList ) {
            list.add( toDto( console ) );
        }

        return list;
    }

    @Override
    public ConsoleDTO toDto(Console console) {
        if ( console == null ) {
            return null;
        }

        ConsoleDTO consoleDTO = new ConsoleDTO();

        Long id = consoleDefaultEmulatorId( console );
        if ( id != null ) {
            consoleDTO.setDefaultEmulatorId( id );
        }
        consoleDTO.setId( console.getId() );
        consoleDTO.setAbbreviation( console.getAbbreviation() );
        consoleDTO.setName( console.getName() );
        consoleDTO.setPathIcon( console.getPathIcon() );
        consoleDTO.setManufacturer( console.getManufacturer() );
        consoleDTO.setGeneration( console.getGeneration() );
        consoleDTO.setBits( console.getBits() );
        consoleDTO.setResume( console.getResume() );
        consoleDTO.setEmulators( emulatorSetToEmulatorDTOSet( console.getEmulators() ) );

        return consoleDTO;
    }

    @Override
    public Console toEntity(ConsoleDTO consoleDTO) {
        if ( consoleDTO == null ) {
            return null;
        }

        Console console = new Console();

        console.setDefaultEmulator( emulatorMapper.fromId( consoleDTO.getDefaultEmulatorId() ) );
        console.setId( consoleDTO.getId() );
        console.setAbbreviation( consoleDTO.getAbbreviation() );
        console.setName( consoleDTO.getName() );
        console.setPathIcon( consoleDTO.getPathIcon() );
        console.setManufacturer( consoleDTO.getManufacturer() );
        console.setGeneration( consoleDTO.getGeneration() );
        console.setBits( consoleDTO.getBits() );
        console.setResume( consoleDTO.getResume() );
        console.setEmulators( emulatorDTOSetToEmulatorSet( consoleDTO.getEmulators() ) );

        return console;
    }

    private Long consoleDefaultEmulatorId(Console console) {
        if ( console == null ) {
            return null;
        }
        Emulator defaultEmulator = console.getDefaultEmulator();
        if ( defaultEmulator == null ) {
            return null;
        }
        Long id = defaultEmulator.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Set<EmulatorDTO> emulatorSetToEmulatorDTOSet(Set<Emulator> set) {
        if ( set == null ) {
            return null;
        }

        Set<EmulatorDTO> set1 = new HashSet<EmulatorDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Emulator emulator : set ) {
            set1.add( emulatorMapper.toDto( emulator ) );
        }

        return set1;
    }

    protected Set<Emulator> emulatorDTOSetToEmulatorSet(Set<EmulatorDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Emulator> set1 = new HashSet<Emulator>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( EmulatorDTO emulatorDTO : set ) {
            set1.add( emulatorMapper.toEntity( emulatorDTO ) );
        }

        return set1;
    }
}
