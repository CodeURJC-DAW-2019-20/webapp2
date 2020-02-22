package com.practica.model;


import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Tournament {
	
	@Id	
	private String name;
	
	public Tournament() {		
	}
	
	public Tournament(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
