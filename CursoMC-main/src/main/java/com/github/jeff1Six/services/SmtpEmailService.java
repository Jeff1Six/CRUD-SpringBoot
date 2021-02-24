package com.github.jeff1Six.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService{

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMail;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Smiluando envio de email");
		mailSender.send(msg);
		LOG.info("EMAIL ENVIADO");
	}


	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Smiluando envio de email");
		javaMail.send(msg);
		LOG.info("EMAIL ENVIADO");		
	}

}
