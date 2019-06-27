package fr.provenzano.webemul.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import fr.provenzano.webemul.service.GenreService;
import fr.provenzano.webemul.service.RomService;
import fr.provenzano.webemul.service.TechParameterService;
import fr.provenzano.webemul.service.TheGamesDbService;
import fr.provenzano.webemul.service.dto.ApiInformationDTO;
import fr.provenzano.webemul.service.dto.GenreDTO;
import fr.provenzano.webemul.service.dto.RomDTO;
import fr.provenzano.webemul.service.errors.BadParameterException;
import net.thegamesdb.Cover;
import net.thegamesdb.Game;
import net.thegamesdb.Game_;

@Service
public class TheGamesDbServiceImpl implements TheGamesDbService {

	private final ProxyManager urlConnectionProxy;

	private final TechParameterService parameterService;
	
	private final RomService romService;

	private ApiInformationDTO apiInformationDTO;
	
	private final GenreService genreService;

	public TheGamesDbServiceImpl(RomService romService, ProxyManager urlConnectionProxy, TechParameterService parameterService, GenreService genreService) {
		
		this.romService = romService;
		this.urlConnectionProxy = urlConnectionProxy;
		this.parameterService = parameterService;		
		this.genreService = genreService;
	}

	private ApiInformationDTO getApiParameters() {

		ApiInformationDTO apiInformationDTO = new ApiInformationDTO();

		apiInformationDTO.setApiUrl(parameterService.findByName("api.url").getValue());
		apiInformationDTO.setApiKey(parameterService.findByName("api.key").getValue());
		apiInformationDTO.setGamesUrl(parameterService.findByName("api.games.url").getValue());
		apiInformationDTO.setImagesUrl(parameterService.findByName("api.images.url").getValue());
		apiInformationDTO.setImagesBaseUrl(parameterService.findByName("api.images.download.url").getValue());
		

		return apiInformationDTO;
	}

	public List<Game_> getGames(String name, Integer platform) throws BadParameterException {

		this.urlConnectionProxy.initConnection();
		this.apiInformationDTO = getApiParameters();		
		
		List<Game_> result = new ArrayList<>();

		try {

			String finalUrl = apiInformationDTO.getApiUrl() + apiInformationDTO.getGamesUrl();
			finalUrl= finalUrl.replace("%api.key%", apiInformationDTO.getApiKey());
			finalUrl = finalUrl.replace("%game.name%", name.replaceAll(" ", "%20"));
			if (platform != null) {
				finalUrl = finalUrl.replace("%platform%", Integer.toString(platform));				
			}

			HttpResponse<JsonNode> response = Unirest.get(finalUrl).header("Accept", "application/json").asJson();
			JsonNode node = response.getBody();

			JSONArray array = node.getArray();
			for (int i = 0; i < array.length(); i++) {
				try {
					JSONObject jsonObject = (JSONObject) array.get(i);
					ObjectMapper mapper = new ObjectMapper();
					Game game = mapper.readValue(jsonObject.toString(), Game.class);
					for (Game_ game_ : game.getData().getGames()) {						
						result.add(game_);
					}
				} catch (Exception e) {
					throw new BadParameterException(e.getMessage());
				}
			}

		} catch (UnirestException e) {
			throw new BadParameterException(e.getMessage());
		}
		return result;
	}
	
	public void updateInformation(Long id, Long romId) throws BadParameterException {
		
		this.urlConnectionProxy.initConnection();
		
		try {
			String finalUrl = apiInformationDTO.getApiUrl() + "Games/ByGameID?apikey=%api.key%&id=%game.id%&fields=genres";
			finalUrl = finalUrl.replace("%api.key%", apiInformationDTO.getApiKey());
			finalUrl = finalUrl.replace("%game.id%", Long.toString(id));
			
			HttpResponse<JsonNode> response = Unirest.get(finalUrl).header("Accept", "application/json").asJson();
			JsonNode node = response.getBody();
			
			Set<GenreDTO> genres = new HashSet<>();
			RomDTO romDTO = romService.findOne(romId);
					
			JSONArray array = node.getArray();
			for (int i = 0; i < array.length(); i++) {
					try {
						JSONObject jsonObject = (JSONObject) array.get(i);
						JSONObject jsonDataObject = (JSONObject) jsonObject.get("data");
						JSONArray jsonGamesObject = (JSONArray) jsonDataObject.get("games");
						JSONObject jsonGameObject = (JSONObject) jsonGamesObject.get(i);
						JSONArray jsonGenresObject = (JSONArray) jsonGameObject.get("genres");
						String releaseDate = (String) jsonGameObject.get("release_date");
						if (StringUtils.isNotBlank(releaseDate)) {							
							try {
								romDTO.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
							} catch (ParseException e) {								
							}
						}
						for (int j = 0; j < jsonGenresObject.length(); j++) {
							Integer genre = (Integer) jsonGenresObject.get(j);
							GenreDTO genreDTO = genreService.findOne(genre.longValue());
							if (genreDTO!=null) {
								genres.add(genreDTO);
							}
						}
						
					} catch (JSONException e) {
						throw new BadParameterException(e.getMessage());
					}		
			}
			romDTO.setGenres(genres);
			romService.save(romDTO);
		} catch (UnirestException e) {
			throw new BadParameterException(e.getMessage());
		}
	}
	

	@Override
	public void downloadCover(Long id, Long romId) throws BadParameterException {

		this.urlConnectionProxy.initConnection();
		this.apiInformationDTO = getApiParameters();	
		
		try {

			String finalUrl = apiInformationDTO.getApiUrl() + apiInformationDTO.getImagesUrl();
			finalUrl = finalUrl.replace("%api.key%", apiInformationDTO.getApiKey());
			finalUrl = finalUrl.replace("%game_id%", Long.toString(id));
			
			HttpResponse<JsonNode> response = Unirest.get(finalUrl).header("Accept", "application/json").asJson();
			JsonNode node = response.getBody();

			boolean coverDownloaded = false;
			JSONArray array = node.getArray();
			for (int i = 0; i < array.length() && !coverDownloaded; i++) {
				try {
					JSONObject jsonObject = (JSONObject) array.get(i);
					JSONObject jsonDataObject = (JSONObject) jsonObject.get("data");
					JSONObject jsonImagesObject = (JSONObject) jsonDataObject.get("images");
					JSONArray jsonImagesArray = (JSONArray) jsonImagesObject.get(Long.toString(id));
					for (int j = 0; j < jsonImagesArray.length(); j++) {
						JSONObject jsonImage = (JSONObject) jsonImagesArray.get(j);
						ObjectMapper mapper = new ObjectMapper();
						Cover cover = mapper.readValue(jsonImage.toString(), Cover.class);
						String type = cover.getType();
						String side = (String) cover.getSide();
						if (type.equals("boxart") && side.equals("front")) {
							try {
								String urlString = apiInformationDTO.getImagesBaseUrl() + cover.getFilename();
								byte[] imageAsByte = urlConnectionProxy.getBytes(urlString);
								RomDTO romDTO = romService.findOne(romId);
								romDTO.setCover(imageAsByte);
								romService.save(romDTO);
								coverDownloaded = true;
								break;
							} catch (Exception e) {
								throw new BadParameterException(e.getMessage());
							}

						}
					}
				} catch (Exception e) {
					throw new BadParameterException(e.getMessage());
				}
			}
		} catch (UnirestException e) {
			throw new BadParameterException(e.getMessage());
		}
	}

}
