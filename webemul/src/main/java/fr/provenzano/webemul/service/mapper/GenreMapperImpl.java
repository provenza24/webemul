package fr.provenzano.webemul.service.mapper;

import fr.provenzano.webemul.domain.Genre;
import fr.provenzano.webemul.service.dto.GenreDTO;
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
public class GenreMapperImpl implements GenreMapper {

    @Override
    public Genre toEntity(GenreDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Genre genre = new Genre();

        genre.setId( dto.getId() );
        genre.setName( dto.getName() );
        genre.setIgdbID( dto.getIgdbID() );

        return genre;
    }

    @Override
    public GenreDTO toDto(Genre entity) {
        if ( entity == null ) {
            return null;
        }

        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setId( entity.getId() );
        genreDTO.setName( entity.getName() );
        genreDTO.setIgdbID( entity.getIgdbID() );

        return genreDTO;
    }

    @Override
    public List<Genre> toEntity(List<GenreDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Genre> list = new ArrayList<Genre>( dtoList.size() );
        for ( GenreDTO genreDTO : dtoList ) {
            list.add( toEntity( genreDTO ) );
        }

        return list;
    }

    @Override
    public List<GenreDTO> toDto(List<Genre> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GenreDTO> list = new ArrayList<GenreDTO>( entityList.size() );
        for ( Genre genre : entityList ) {
            list.add( toDto( genre ) );
        }

        return list;
    }
}
