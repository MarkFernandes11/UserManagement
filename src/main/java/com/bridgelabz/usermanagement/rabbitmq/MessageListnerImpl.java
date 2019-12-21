package com.bridgelabz.usermanagement.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.usermanagement.dto.Email;


@Service
public class MessageListnerImpl implements MessageListener {

	@Autowired
	private SendMailToUser sendMailToUser;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void receiveMessage(Email email) {
		logger.debug("Sending message to "+email.getTo());
		
		sendMailToUser.sendMail(email.getTo(), email.getSubject(), email.getBody());
		
	}


}
