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
        String msgBody="Welcome to TenniShip! These are your credentials:\nUser: " + user.getEmail() + "\nPassword: " + user.getPasswordHash()+
        		"\n\nIf you have any issue, please contact to tennishipSpring@gmail.com.";
        msg.setText(msgBody);
        
        javaMailSender.send(msg);
    }
	
	public void sendDisqualificationEmail(User user, String tournament) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setSubject("You've been disqualified from "+tournament+" tournament");
        String msgBody="Dear "+user.getUserName()+"we regret to comunicate you that your team " + user.getTeam() + 
        		"has been disqualified from " + tournament + " tournament.\n If you have any complaint or you would like to get more information"+
        		"please, contact to tennishipSpring@gmail.com.";
        msg.setText(msgBody);
        
        javaMailSender.send(msg);
    }

}
