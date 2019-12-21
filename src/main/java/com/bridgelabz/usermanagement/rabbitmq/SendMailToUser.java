package com.bridgelabz.usermanagement.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendMailToUser 
{	
	@Autowired
	private JavaMailSender javaMailSender; 
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @param to indicates To whom the mail is to be sent
	 * @param contains subject of the mail
	 * @param contains body of the mail
	 */
	public void sendMail(String to, String subject, String body) 
	{
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setFrom("me608889@gmail.com");
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(body);
		
		javaMailSender.send(simpleMailMessage);
		
		
		logger.debug("Message sent to "+to);
	}
}
