package com.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursomc.security.JWTUtil;
import com.cursomc.security.UserSS;
import com.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {

		UserSS user = UserService.authenticated();
		String token = this.jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);

		return ResponseEntity.noContent().build();
	}

}
