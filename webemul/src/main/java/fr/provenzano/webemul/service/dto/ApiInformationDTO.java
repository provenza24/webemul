package fr.provenzano.webemul.service.dto;

public class ApiInformationDTO {

	private String apiUrl;
	
	private String gamesUrl;
	
	private String imagesUrl;
	
	private String apiKey;

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getGamesUrl() {
		return gamesUrl;
	}

	public void setGamesUrl(String gamesUrl) {
		this.gamesUrl = gamesUrl;
	}

	public String getImagesUrl() {
		return imagesUrl;
	}

	public void setImagesUrl(String imagesUrl) {
		this.imagesUrl = imagesUrl;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	
}
