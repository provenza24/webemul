package fr.provenzano.webemul.repository;

import fr.provenzano.webemul.domain.Emulator;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Emulator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmulatorRepository extends JpaRepository<Emulator, Long> {

}
