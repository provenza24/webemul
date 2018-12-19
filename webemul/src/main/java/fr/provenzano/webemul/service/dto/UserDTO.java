package fr.provenzano.webemul.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable{	
	
	private static final long serialVersionUID = 1L;

	private String firstname;
	
	private String lastname;
	
	private Integer authorization;
	
	private List<String> authorizations;
	
	public UserDTO() {		
		this.authorizations = new ArrayList<>();
	}
	
	public UserDTO(String firstname, String lastname, Integer authorization) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.authorization = authorization;
		this.authorizations = new ArrayList<>();
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Integer authorization) {
		this.authorization = authorization;
	}

	public List<String> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(List<String> authorizations) {
		this.authorizations = authorizations;
	}

	public void addAuthorization(String authorization) {
		this.authorizations.add(authorization);
	}
	
}
