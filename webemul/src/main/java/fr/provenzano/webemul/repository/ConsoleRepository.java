package fr.provenzano.webemul.repository;

import fr.provenzano.webemul.domain.Console;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Console entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsoleRepository extends JpaRepository<Console, Long> {
    @Query("select distinct console from Console console left join fetch console.emulators")
    List<Console> findAllWithEagerRelationships();

    @Query("select console from Console console left join fetch console.emulators where console.id =:id")
    Console findOneWithEagerRelationships(@Param("id") Long id);

}
