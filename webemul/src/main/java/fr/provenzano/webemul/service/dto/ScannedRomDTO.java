package fr.provenzano.webemul.service.dto;

public class ScannedRomDTO {
	
	private String name;
	
	private ScannedRomStatus status;

	public ScannedRomDTO(String name, ScannedRomStatus status) {
		super();
		this.name = name;
		this.status = status;
	}
	
	public static enum ScannedRomStatus {
		ADDED, DELETED;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ScannedRomStatus getStatus() {
		return status;
	}

	public void setStatus(ScannedRomStatus status) {
		this.status = status;
	}
	
}
