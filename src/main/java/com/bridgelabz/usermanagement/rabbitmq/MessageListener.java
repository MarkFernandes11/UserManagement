package com.bridgelabz.usermanagement.rabbitmq;

import com.bridgelabz.usermanagement.dto.Email;

public interface MessageListener {
	
	public void receiveMessage(Email email);
}
