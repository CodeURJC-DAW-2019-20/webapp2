//package com.practica;
//
//import java.util.Date;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MailSenderXX {
//
//	@Autowired
//    private JavaMailSender javaMailSender;
//	
//	@PostConstruct
//	public void sendEmail() {
//
//		System.out.println("xxxxxx");
//		
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("alvarora12@gmail.com", "aj.rivas.2017@alumnos.urjc.es", "d.pascual.2017@alumnos.urjc.es");
//        msg.setBcc("alonsorial@gmail.com");
//        msg.setCc("rivasalcobendas@yahoo.es");
//        msg.setFrom("tennishipSpring@gmail.com");
//        msg.setSentDate(new Date());
//        msg.setReplyTo("jumalon4@yahoo.es");
//        msg.setSubject("Testing from Spring Boot");
//        msg.setText("Hello World \n Spring Boot Email");
//
//        javaMailSender.send(msg);
//
//    }
//
//	
//}
