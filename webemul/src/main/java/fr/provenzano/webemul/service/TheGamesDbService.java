package fr.provenzano.webemul.service;

import java.util.List;

import net.thegamesdb.Game_;

public interface TheGamesDbService {

	public List<Game_> getGames(String name, Integer platform);
	
	public void downloadCover(Long id, Long romId);
	
}
