package com.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	//From application.properties
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		
		SimpleMailMessage mailMessage = prepareSimpleMailMessageFromPedido(pedido);
		
		sendEmail(mailMessage);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setTo(pedido.getCliente().getEmail());//* Quem irá receber
		mailMessage.setFrom(this.sender);//* Quem irá enviar
		mailMessage.setSubject("Pedido "+pedido.getId()+" Confirmado.");//* Assunto
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));//* Data do servidor
		mailMessage.setText(pedido.toString());//*Corpo do   email
		
		return mailMessage;
	}


}
