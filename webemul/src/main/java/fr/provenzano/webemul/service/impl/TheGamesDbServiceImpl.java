package fr.provenzano.webemul.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.xstream.XStream;

import fr.provenzano.webemul.service.RomService;
import fr.provenzano.webemul.service.TheGamesDbService;
import fr.provenzano.webemul.service.dto.RomDTO;
import net.thegamesdb.Cover;
import net.thegamesdb.Game;
import net.thegamesdb.Game_;

@Service
public class TheGamesDbServiceImpl implements TheGamesDbService {

	private static final String API_URL = "https://api.thegamesdb.net/";

	private static final String API_KEY = "79ed4d13b297d968cb10343a441d5dbfb6971aae1acbdcc403b14a4d0abef94a";

	private final RomService romService;
	
	public TheGamesDbServiceImpl(RomService romService) {
		this.romService = romService;
	}
	
	public List<Game_> getGames(String name, Integer platform) {
		
		List<Game_> result = new ArrayList<>();
	
		addProxy();

		try {
		
			String finalUrl = API_URL + "Games/ByGameName?";
			finalUrl += "apikey=" + API_KEY;
			finalUrl += "&name=" + name.replaceAll(" ", "%20");
			if (platform!=null) {
				finalUrl += "&filter[platform][]="+Integer.toString(platform);
			}			

			HttpResponse<JsonNode> response = Unirest.get(finalUrl).header("Accept", "application/json").asJson();
			JsonNode node = response.getBody();			
			//toFile(node, "gamelist.xml");
			//JsonNode node = fromFile("gamelist.xml");
			
			JSONArray array = node.getArray();
			for (int i = 0; i < array.length(); i++) {
				try {
					JSONObject jsonObject = (JSONObject) array.get(i);
					ObjectMapper mapper = new ObjectMapper();
					Game game = mapper.readValue(jsonObject.toString(), Game.class);
					for (Game_ game_ : game.getData().getGames()) {
						System.out.println(game_.toString());
						result.add(game_);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (UnirestException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	private void addProxy() {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("PROVENZANO-00494", "ZuZ6*5!"));
		clientBuilder.useSystemProperties();
		clientBuilder.setProxy(new HttpHost("proxy-web.cnamts.fr", 3128));
		clientBuilder.setDefaultCredentialsProvider(credsProvider);
		clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());

		Lookup<AuthSchemeProvider> authProviders = RegistryBuilder.<AuthSchemeProvider> create()
				.register(AuthSchemes.BASIC, new BasicSchemeFactory()).build();
		clientBuilder.setDefaultAuthSchemeRegistry(authProviders);

		Unirest.setHttpClient(clientBuilder.build());
	}

	@Override
	public void downloadCover(Long id, Long romId) {

		addProxy();

		try {
		
			String finalUrl = API_URL + "Games/Images?";
			finalUrl += "apikey=" + API_KEY;
			finalUrl += "&games_id=" + Long.toString(id);

			HttpResponse<JsonNode> response = Unirest.get(finalUrl).header("Accept", "application/json").asJson();
			JsonNode node = response.getBody();
			//toFile(node, "covers.xml");
			
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
						System.out.println(cover.toString());
						String type = cover.getType();
						String side = (String)cover.getSide();
						if (type.equals("boxart") && side.equals("front")) {
							URL url = new URL("https://cdn.thegamesdb.net/images/small/"+cover.getFilename());
							InputStream in = new BufferedInputStream(url.openStream());
							ByteArrayOutputStream out = new ByteArrayOutputStream();
							byte[] buf = new byte[1024];
							int n = 0;
							while (-1!=(n=in.read(buf)))
							{
							   out.write(buf, 0, n);
							}
							out.close();
							in.close();
							byte[] imageAsByte = out.toByteArray();
							RomDTO romDTO = romService.findOne(romId);
							romDTO.setCover(imageAsByte);
							romService.save(romDTO);
							coverDownloaded = true;
							break;
						}
						j++;
					}															
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (UnirestException e1) {
			e1.printStackTrace();
		}
	}
	
	private void toFile(JsonNode node, String filename) {
				
		try {
			XStream xstream = new XStream();
			xstream.toXML(node, new FileOutputStream(new File(filename)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private JsonNode fromFile(String filename) {
		try {
			XStream xstream = new XStream();
			return (JsonNode)xstream.fromXML(new FileInputStream(new File(filename)));			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
