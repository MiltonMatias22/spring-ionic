package com.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(MailSender.class);
	
	@Override
	public void sendEmail(SimpleMailMessage mailMessage) {
		LOG.info("-- || -- -- -- ENVIANDO EMAIL -- -- -- || --");
		mailSender.send(mailMessage);
		LOG.info("      -- --  Email Enviado! -- --");
	}

	@Override
	public void sendHtmlEmail(MimeMessage mimeMessage) {
		LOG.info("-- || -- -- -- ENVIANDO EMAIL -- -- -- || --");
		javaMailSender.send(mimeMessage);
		LOG.info("      -- --  Email Enviado! -- --");		
	}

}
