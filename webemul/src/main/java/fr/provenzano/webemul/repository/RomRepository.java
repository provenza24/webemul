package fr.provenzano.webemul.repository;

import fr.provenzano.webemul.domain.Rom;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Rom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RomRepository extends JpaRepository<Rom, Long>, JpaSpecificationExecutor<Rom> {
    @Query("select distinct rom from Rom rom left join fetch rom.genres")
    List<Rom> findAllWithEagerRelationships();

    @Query("select rom from Rom rom left join fetch rom.genres where rom.id =:id")
    Rom findOneWithEagerRelationships(@Param("id") Long id);
    
    Rom findByPathFile(String pathFile);

    @Query("select rom from Rom rom where rom.console.id =:consoleId")
    List<Rom> findConsoleRoms(@Param("consoleId") Long consoleId);
    
}
