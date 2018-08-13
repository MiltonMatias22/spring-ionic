package com.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.cursomc.domain.Cliente;
import com.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender; 
	
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
	
	protected String htmlFromTemplatePedido(Pedido pedido) {
		
		Context context = new Context();//*Acessa o template html
		context.setVariable("pedido", pedido);
		
		return this.templateEngine.process("email/confirmacaoPedido", context); //*Processar o template
		
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		
		try {
		
			MimeMessage mimeMessage = prepareSimpleMimeMessageFromPedido(pedido);
			sendHtmlEmail(mimeMessage);
		
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(pedido);
		}
		
	}

	protected MimeMessage prepareSimpleMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		
		MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
		
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);		
		messageHelper.setTo(pedido.getCliente().getEmail());//* Quem irá receber
		messageHelper.setFrom(this.sender);//* Quem irá enviar
		messageHelper.setSubject("Pedido "+pedido.getId()+" Confirmado.");//* Assunto
		messageHelper.setSentDate(new Date(System.currentTimeMillis()));//* Data do servidor
		messageHelper.setText(htmlFromTemplatePedido(pedido), true);//*Corpo do   email
		
		return mimeMessage ;
	}
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPassword){

		SimpleMailMessage mailMessage = prepareNewPasswordEmail(cliente, newPassword);
		
		sendEmail(mailMessage);
	}

	private SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPassword) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setTo(cliente.getEmail());//* Quem irá receber
		mailMessage.setFrom(this.sender);//* Quem irá enviar
		mailMessage.setSubject("Solicição de nova senha");//* Assunto
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));//* Data do servidor
		mailMessage.setText("Nova senha: "+ newPassword);//*Corpo do   email
		
		return mailMessage;
	}


}
