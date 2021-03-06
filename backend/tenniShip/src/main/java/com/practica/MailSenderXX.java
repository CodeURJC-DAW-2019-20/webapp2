package com.practica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.practica.security.User;

@Service("mailSenderXX")
public class MailSenderXX {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendConfirmationEmail(User user) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(user.getEmail());
		msg.setSubject("Register Confirmation e-Mail");
		String msgBody = "Welcome to TenniShip!\nYou successfully registered " + user.getTeam()
				+ " as a team.\nThese is your user name, use it for sign in:\nUser: " + user.getUserName() +
				"\n\nIf you have any issue, please contact to tennishipSpring@gmail.com.";
		msg.setText(msgBody);

		javaMailSender.send(msg);
	}


}