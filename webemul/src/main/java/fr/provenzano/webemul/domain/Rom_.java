package fr.provenzano.webemul.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rom.class)
public abstract class Rom_ {

	public static volatile SingularAttribute<Rom, Console> console;
	public static volatile SingularAttribute<Rom, String> extension;
	public static volatile SingularAttribute<Rom, String> pathCover;
	public static volatile SetAttribute<Rom, Genre> genres;
	public static volatile SingularAttribute<Rom, String> name;
	public static volatile SingularAttribute<Rom, Long> id;
	public static volatile SingularAttribute<Rom, String> pathFile;

}

