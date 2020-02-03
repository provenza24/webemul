package fr.provenzano.webemul.service.dto;

public class RomSearchDTO {

	private Long consoleId;			
	
	private String gameName;
	
	private Long genreId;
	
	private String firstLetterRange;

	public RomSearchDTO() {		
	}
	
	public Long getConsoleId() {
		return consoleId;
	}

	public void setConsoleId(Long consoleId) {
		this.consoleId = consoleId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Long getGenreId() {
		return genreId;
	}

	public void setGenreId(Long genreId) {
		this.genreId = genreId;
	}

	public String getFirstLetterRange() {
		return firstLetterRange;
	}

	public void setFirstLetterRange(String firstLetterRange) {
		this.firstLetterRange = firstLetterRange;
	}
	
	
}
