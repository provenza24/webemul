package fr.provenzano.webemul.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import callback.OnSuccessCallback;
import wrapper.Endpoints;
import wrapper.IGDBWrapper;
import wrapper.Parameters;
import wrapper.Version;


@EnableScheduling
@Component
public class ScheduleService {

	private final Logger log = LoggerFactory.getLogger(ScheduleService.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron = "30 45 10 * * ?")
	public void reportCurrentTime() {
		log.info("The time is now {}", dateFormat.format(new Date()));
		
		IGDBWrapper wrapper = new IGDBWrapper("6807538006d5178a703e460aae52e639", Version.STANDARD, false);

		/*
		Search for up to two Atari platforms and return their names */
		Parameters params = new Parameters()
			.addSearch("Atari")
			.addFields("name")
			.addLimit("2");

		wrapper.search(Endpoints.PLATFORMS, params, new OnSuccessCallback(){
			@Override
		        public void onSuccess(JSONArray result) {
		        	// JSONArray containing 2 Atari platforms
					System.out.println("Success");
		        }

		        @Override
		        public void onError(Exception error) {
		        	System.out.println("error");
		        }
		});

		
		/*
		try {
			HttpResponse<JsonNode> response = Unirest.get("https://api-endpoint.igdb.com/characters/?search=mario&fields=*&limit=10")
			.header("user-key", "6807538006d5178a703e460aae52e639")
			.header("Accept", "application/json")
			.asJson();
			System.out.println(response.getStatus()+"-");
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
				
	}


}
