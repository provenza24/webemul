package fr.provenzano.webemul.service.dto;

public class ScanDTO {

	private String message;
	
	private Integer progression;

	public ScanDTO(String message, Integer progression) {
		super();
		this.message = message;
		this.progression = progression;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getProgression() {
		return progression;
	}

	public void setProgression(Integer progression) {
		this.progression = progression;
	}
	
}
