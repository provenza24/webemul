package fr.provenzano.webemul.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.provenzano.webemul.service.UserService;
import fr.provenzano.webemul.service.dto.UserDTO;
import fr.provenzano.webemul.service.enums.AuthorizationEnum;
import fr.cnamts.securite.filtres.bean.ListeOfServiceBean;
import fr.cnamts.securite.filtres.bean.ProfileBean;
import fr.cnamts.securite.filtres.bean.ServiceBean;

/**
 * Service Implementation for managing User.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    @Qualifier("userAuthentication")
    private AuthenticationManager authenticationManager;
           
    public UserServiceImpl() {    	
    }

	@Override
	public UserDTO getUser(ProfileBean profileBean) {
		
		UserDTO user = buildUserDTO(profileBean);				
		UsernamePasswordAuthenticationToken authReq = buildUsernamePasswordAuthenticationToken(user);	    	
    	Authentication auth = authenticationManager.authenticate(authReq);
    	SecurityContextHolder.getContext().setAuthentication(auth);	        	    	
        return user;				
	}
	
	/**
	 * Build UserDTO.
	 * Currently:	
	 * @param serviceBeans
	 * @return
	 */
	private UserDTO buildUserDTO(ProfileBean profileBean) {
    	
		UserDTO user = new UserDTO();
		user.setLastname(profileBean.getNomAgent());
		user.setFirstname(profileBean.getPrenomAgent());
		ListeOfServiceBean serviceBeans = profileBean.getListeSystemes().getSystemeBean(0).getListeService();
		Integer authorization = 0;
		for(int i=0; i<serviceBeans.size();i++) {
    		ServiceBean serviceBean = (ServiceBean)serviceBeans.get(i);
    		AuthorizationEnum authEnum = AuthorizationEnum.valueOf(serviceBean.getNom());
    		if (authEnum!=null) {
    			user.addAuthorization(authEnum.getRole());
    			if (authEnum.getValue()>authorization) {
        			authorization = authEnum.getValue();
    			}
    		}    		
    	}    	
		user.setAuthorization(authorization);
    	return user;    	
    }
		
	/**
	 * Build a UsernamePasswordAuthenticationToken used to store security data in Spring Session 
	 * @param profileBean
	 * @param serviceBeans
	 * @return
	 */
	private UsernamePasswordAuthenticationToken buildUsernamePasswordAuthenticationToken(UserDTO userDTO) {
    	
    	List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    	
    	for (String authorization : userDTO.getAuthorizations()) {
    		authorities.add(new SimpleGrantedAuthority(authorization));
		}
    	    	    
    	UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDTO.getLastname(), "", authorities);
    	return result;    	    	
    }
	
	/**
     * Retrieve matricule from session
     * @param request
     * @return
     */
	public String getMatricule(HttpServletRequest request) {
		HttpSession session = request.getSession();	    	        
        ProfileBean profileBean = (ProfileBean) session.getAttribute("ARAMISBEAN");
        String matricule = profileBean.getNomISM().toUpperCase();
		return matricule;
	}
    
}
