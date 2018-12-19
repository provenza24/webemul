package fr.provenzano.webemul.service.mapper;

import fr.provenzano.webemul.domain.*;
import fr.provenzano.webemul.service.dto.GenreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Genre and its DTO GenreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GenreMapper extends EntityMapper<GenreDTO, Genre> {



    default Genre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(id);
        return genre;
    }
}
