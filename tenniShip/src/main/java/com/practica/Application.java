package com.practica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//sendEmail();
		SpringApplication.run(Application.class, args);
	}
	
//	@Autowired
//    private static JavaMailSender javaMailSender;
//	
//	static void sendEmail() {
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

}
