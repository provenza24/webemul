package fr.provenzano.webemul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.provenzano.webemul.domain.Parameter;


/**
 * Spring Data JPA repository for the Parameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

	public Parameter findByName(String name);
	
}
