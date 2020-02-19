package com.practica.security;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


@Component
@SessionScope
public class UserComponent {

	private User user;

	private String team;
	
	public void setTeam(User user) {
		this.team = user.getTeam();
	}
	
	public String getTeam() {
		return team;
	}
	
	public User getLoggedUser() {
		return user;
	}

	public void setLoggedUser(User user) {
		this.user = user;
	}

	public boolean isLoggedUser() {
		return this.user != null;
	}

}
