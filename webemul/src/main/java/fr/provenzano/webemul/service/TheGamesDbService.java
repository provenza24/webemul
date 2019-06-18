package fr.provenzano.webemul.service;

import java.util.List;

import fr.provenzano.webemul.service.errors.BadParameterException;
import net.thegamesdb.Game_;

public interface TheGamesDbService {

	public List<Game_> getGames(String name, Integer platform) throws BadParameterException;
	
	public void downloadCover(Long id, Long romId) throws BadParameterException;
	
	public void updateInformation(Long id, Long romId) throws BadParameterException;
	
}
