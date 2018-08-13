package com.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cliente;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.services.exception.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository; 
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random(); 
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = this.clienteRepository.findByEmail(email);
		
		if (cliente == null) {
			
			throw new ObjectNotFoundException("Email não encontrado");
			
		}
		
		String newPassword = newPassword();
		
		cliente.setSenha(bCryptPasswordEncoder.encode(newPassword));
		
		this.clienteRepository.save(cliente);
		
		this.emailService.sendNewPasswordEmail(cliente, newPassword);
	}

	private String newPassword() {
		
		char[] vet = new char[10];
		
		for(int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		
		return new String(vet);
	}

	private char randomChar() {
		
		int opt = this.random.nextInt(3);

		if(opt == 0) { //*gera um dígito			
			return (char) (this.random.nextInt(10) + 48);
			
		}else if(opt == 1){ //*Gera letra maiúscula			
			return (char) (this.random.nextInt(26) + 65);
			
		}else { // Gera letra minúscula			
			return (char) (this.random.nextInt(26) + 97);
			
		}
	}
}
