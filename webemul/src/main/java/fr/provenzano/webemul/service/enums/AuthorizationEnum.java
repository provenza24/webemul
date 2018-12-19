package fr.provenzano.webemul.service.enums;

public enum AuthorizationEnum {

	DL06_WEBEMUL_UTILISATEUR(0, "ROLE_UTILISATEUR", "Utilisateur"),
	DL06_WEBEMUL_ADMIN(1, "ROLE_ADMIN", "Admin");	
	
	private Integer value = 0;
		
	private String role;
	
	private String uiDisplay;
	
	AuthorizationEnum(Integer value, String role, String uiDisplay) {
		this.value = value;
		this.role = role;
		this.uiDisplay = uiDisplay;
	}

	public Integer getValue() {
		return this.value;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUiDisplay() {
		return uiDisplay;
	}

}
