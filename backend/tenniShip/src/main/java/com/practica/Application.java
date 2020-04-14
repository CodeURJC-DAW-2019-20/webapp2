package com.practica;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application {
	
	@Configuration
	public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

	    @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/new").setViewName("redirect:/new/");
	        registry.addViewController("/new/").setViewName("forward:/new/index.html");
	    super.addViewControllers(registry);
	    }
	}

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("tennishipSpring@gmail.com");
		mailSender.setPassword("tenspring");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		return mailSender;
	}

}