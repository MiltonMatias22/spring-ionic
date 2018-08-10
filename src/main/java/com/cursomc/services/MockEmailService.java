package com.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage mailMessage) {

		LOG.info("\n\n-- || -- -- -- SIMULANDO ENVIO DE EMAIL -- -- -- || --\n"+
						"\n"+mailMessage.toString().replaceAll(";", "\n")+
						"\nEmail Enviado!\n\n");
	}

	@Override
	public void sendHtmlEmail(MimeMessage mimeMessage) {
		LOG.info("\n\n-- || -- -- -- SIMULANDO ENVIO DE EMAIL HTML-- -- -- || --\n"+
				"\n"+mimeMessage.toString().replaceAll(";", "\n")+
				"\nEmail Enviado!\n\n");
	}

}
