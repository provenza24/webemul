package fr.provenzano.webemul.service.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.provenzano.webemul.domain.Console;
import fr.provenzano.webemul.domain.Genre;
import fr.provenzano.webemul.domain.Rom;
import fr.provenzano.webemul.service.dto.GenreDTO;
import fr.provenzano.webemul.service.dto.RomDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-12-18T11:29:30+0100",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class RomMapperImpl implements RomMapper {

    @Autowired
    private ConsoleMapper consoleMapper;
    @Autowired
    private GenreMapper genreMapper;

    @Override
    public List<Rom> toEntity(List<RomDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Rom> list = new ArrayList<Rom>( dtoList.size() );
        for ( RomDTO romDTO : dtoList ) {
            list.add( toEntity( romDTO ) );
        }

        return list;
    }

    @Override
    public List<RomDTO> toDto(List<Rom> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RomDTO> list = new ArrayList<RomDTO>( entityList.size() );
        for ( Rom rom : entityList ) {
            list.add( toDto( rom ) );
        }

        return list;
    }

    @Override
    public RomDTO toDto(Rom rom) {
        if ( rom == null ) {
            return null;
        }

        RomDTO romDTO = new RomDTO();

        Long id = romConsoleId( rom );
        if ( id != null ) {
            romDTO.setConsoleId( id );
        }
        
        byte[] image = rom.getCover();
        if ( image != null ) {
        	romDTO.setCover( Arrays.copyOf( image, image.length ) );
        }	
        romDTO.setCoverContentType( rom.getCoverContentType() );
        
        romDTO.setId( rom.getId() );
        romDTO.setName( rom.getName() );
        romDTO.setReleaseDate( rom.getReleaseDate() );
        romDTO.setPathFile( rom.getPathFile() );
        romDTO.setExtension( rom.getExtension() );
        romDTO.setPathCover( rom.getPathCover() );
        romDTO.setGenres( genreSetToGenreDTOSet( rom.getGenres() ) );

        return romDTO;
    }

    @Override
    public Rom toEntity(RomDTO romDTO) {
        if ( romDTO == null ) {
            return null;
        }

        Rom rom = new Rom();
        
        byte[] image = romDTO.getCover();
        if ( image != null ) {
        	rom.setCover( Arrays.copyOf( image, image.length ) );
        }
        rom.setCoverContentType( romDTO.getCoverContentType() );

        rom.setConsole( consoleMapper.fromId( romDTO.getConsoleId() ) );
        rom.setId( romDTO.getId() );
        rom.setName( romDTO.getName() );
        rom.setReleaseDate( romDTO.getReleaseDate() );
        rom.setPathFile( romDTO.getPathFile() );
        rom.setExtension( romDTO.getExtension() );
        rom.setPathCover( romDTO.getPathCover() );
        rom.setGenres( genreDTOSetToGenreSet( romDTO.getGenres() ) );

        return rom;
    }

    private Long romConsoleId(Rom rom) {
        if ( rom == null ) {
            return null;
        }
        Console console = rom.getConsole();
        if ( console == null ) {
            return null;
        }
        Long id = console.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Set<GenreDTO> genreSetToGenreDTOSet(Set<Genre> set) {
        if ( set == null ) {
            return null;
        }

        Set<GenreDTO> set1 = new HashSet<GenreDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Genre genre : set ) {
            set1.add( genreMapper.toDto( genre ) );
        }

        return set1;
    }

    protected Set<Genre> genreDTOSetToGenreSet(Set<GenreDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Genre> set1 = new HashSet<Genre>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( GenreDTO genreDTO : set ) {
            set1.add( genreMapper.toEntity( genreDTO ) );
        }

        return set1;
    }
}
