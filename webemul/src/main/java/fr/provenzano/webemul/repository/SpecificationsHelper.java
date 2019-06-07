package fr.provenzano.webemul.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class SpecificationsHelper<T> {

	private Specifications<T> specifications;
	
	public SpecificationsHelper() {
		this.specifications = null;
	}
	
	public void addSpecification(Specification<T> specification) {
    	if (specifications==null) {
    		specifications = Specifications.where(specification);
    	} else {
    		specifications = specifications.and(specification);
    	}    	
    }

	public Specifications<T> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(Specifications<T> specifications) {
		this.specifications = specifications;
	}
	
}
