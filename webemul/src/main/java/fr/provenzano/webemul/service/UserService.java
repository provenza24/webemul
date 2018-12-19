package fr.provenzano.webemul.service;

import javax.servlet.http.HttpServletRequest;

import fr.provenzano.webemul.service.dto.UserDTO;
import fr.cnamts.securite.filtres.bean.ProfileBean;

/**
 * Service Interface for managing User
 */
public interface UserService {
	
	public UserDTO getUser(ProfileBean profileBean);
	
	public String getMatricule(HttpServletRequest request);
	
}

