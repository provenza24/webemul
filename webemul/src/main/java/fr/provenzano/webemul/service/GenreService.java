package fr.provenzano.webemul.service;

import fr.provenzano.webemul.service.dto.GenreDTO;
import java.util.List;

/**
 * Service Interface for managing Genre.
 */
public interface GenreService {

    /**
     * Save a genre.
     *
     * @param genreDTO the entity to save
     * @return the persisted entity
     */
    GenreDTO save(GenreDTO genreDTO);

    /**
     * Get all the genres.
     *
     * @return the list of entities
     */
    List<GenreDTO> findAll();

    /**
     * Get the "id" genre.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GenreDTO findOne(Long id);

    /**
     * Delete the "id" genre.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
