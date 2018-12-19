package fr.provenzano.webemul.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userAuthentication")
public class UserAuthenticationImpl implements AuthenticationManager {

	public UserAuthenticationImpl() {
		
	}
	
	public Authentication authenticate(Authentication auth) throws UsernameNotFoundException {		
		return new UsernamePasswordAuthenticationToken(auth.getName(), "", auth.getAuthorities());
	}
}