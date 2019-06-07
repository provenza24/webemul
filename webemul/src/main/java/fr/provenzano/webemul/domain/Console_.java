package fr.provenzano.webemul.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Console.class)
public abstract class Console_ {

	public static volatile SingularAttribute<Console, String> pathIcon;
	public static volatile SingularAttribute<Console, Integer> generation;
	public static volatile SingularAttribute<Console, String> resume;
	public static volatile SetAttribute<Console, Rom> roms;
	public static volatile SingularAttribute<Console, String> name;
	public static volatile SingularAttribute<Console, Integer> bits;
	public static volatile SingularAttribute<Console, Long> id;
	public static volatile SingularAttribute<Console, Emulator> defaultEmulator;
	public static volatile SingularAttribute<Console, String> abbreviation;
	public static volatile SingularAttribute<Console, String> manufacturer;
	public static volatile SetAttribute<Console, Emulator> emulators;

}

