package com.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.cursomc.security.UserSS;

public class UserService {
	
	//* Returna usuario logago
	public static UserSS authenticated() {
		try {
			
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		} catch (Exception e) {
			return null;
		}
	}
}
