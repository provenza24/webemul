package fr.provenzano.webemul.web.rest;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import fr.provenzano.webemul.service.ConsoleService;
import fr.provenzano.webemul.service.TheGamesDbService;
import fr.provenzano.webemul.service.dto.ConsoleDTO;
import net.thegamesdb.Game_;

/**
 * REST controller for managing Rom.
 */
@RestController
@RequestMapping("/api")
public class TheGamesDbResource {

	private final Logger log = LoggerFactory.getLogger(TheGamesDbResource.class);

	private final TheGamesDbService theGamesDbService;
	
	private final ConsoleService consoleService;

	public TheGamesDbResource(TheGamesDbService theGamesDbService, ConsoleService consoleService) {
		this.theGamesDbService = theGamesDbService;
		this.consoleService = consoleService;
	}
	
	@GetMapping("/thegamesdb/games")
	@Timed
	public List<Game_> getGames(@RequestParam("name") String name, @RequestParam("consoleId") String consoleId) {		
		log.debug("REST request to get list of games from thegamesdb");

		if (StringUtils.isNotBlank(consoleId)) {
			ConsoleDTO console = consoleService.findOne(Long.parseLong(consoleId));
		}
		
		return theGamesDbService.getGames(name, null);
	}
	
	@GetMapping("/thegamesdb/covers/{id}")
	@Timed
	public void getCover(@PathVariable Long id, @RequestParam("romId") String romId) {		
		log.debug("REST request to download cover from thegamesdb");		
		theGamesDbService.downloadCover(id, Long.parseLong(romId));
	}
	
}
