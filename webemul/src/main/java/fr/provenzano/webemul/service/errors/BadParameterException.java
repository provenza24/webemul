package fr.provenzano.webemul.service.errors;

public class BadParameterException extends Exception {

	private static final long serialVersionUID = 2088472694694464782L;
	
	public BadParameterException() {
	}
	
	public BadParameterException(String string) {
		super(string);
	}

}
