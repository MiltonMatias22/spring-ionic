package com.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(MailSender.class);
	
	@Override
	public void sendEmail(SimpleMailMessage mailMessage) {
		LOG.info("-- || -- -- -- ENVIANDO DE EMAIL -- -- -- || --");
		mailSender.send(mailMessage);
		LOG.info("      -- --  Email Enviado! -- --");
	}

}
