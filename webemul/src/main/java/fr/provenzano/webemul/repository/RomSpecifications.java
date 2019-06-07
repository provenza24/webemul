package fr.provenzano.webemul.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.jpa.domain.Specification;

import fr.provenzano.webemul.domain.Console;
import fr.provenzano.webemul.domain.Rom;

public final class RomSpecifications {

	private RomSpecifications() {}
	
	public static Specification<Rom> compareConsole(SingularAttribute<Rom, Console> attribute, Console console) {
        return new Specification<Rom>() {
            @Override
            public Predicate toPredicate(Root<Rom> root,                                 
                                CriteriaQuery<?> query, 
                                CriteriaBuilder cb) {
            	return cb.equal(root.<Console>get(attribute), console);            	
            }
        };
    }
	
}
