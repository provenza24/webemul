package fr.provenzano.webemul.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.jpa.domain.Specification;
import org.thymeleaf.util.StringUtils;

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
	
	public static Specification<Rom> compareFirstLetter(SingularAttribute<Rom, String> attribute, String range) {
        return new Specification<Rom>() {
            @Override
            public Predicate toPredicate(Root<Rom> root,                  
                                CriteriaQuery<?> query, 
                                CriteriaBuilder cb) {
            	String rangeSup = StringUtils.substring(range, 3, 4);
            	
            	return rangeSup.equals("Z") ? cb.greaterThanOrEqualTo(cb.upper(root.<String>get(attribute)), StringUtils.substring(range, 1, 2)) :  
            			cb.and(
            					cb.greaterThanOrEqualTo(cb.upper(root.<String>get(attribute)), StringUtils.substring(range, 1, 2)),
            					cb.lessThanOrEqualTo(cb.upper(root.<String>get(attribute)), StringUtils.substring(range, 3, 4)));
            }
        };
    }
	
}
