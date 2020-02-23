package com.practica.security;


import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	private String userName;

	private String passwordHash;
	
	@Email
	private String email;
	
	private String team;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;
	
	public User () {
	}
	
	public User(String name,String team,String email,String password, List<String> roles) {
		this.userName = name;
		this.team = team;
		this.email = email;
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
		this.roles = roles;
	}
	
	public User(String name,String email,String password, List<String> roles) {
		this.userName = name;
		this.email = email;
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
		this.roles = roles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setName(String name) {
		this.userName = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passwordHash=" + passwordHash + ", email=" + email
				+ ", team=" + team + ", roles=" + roles + "]";
	}

}
