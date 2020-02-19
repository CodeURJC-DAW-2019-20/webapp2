package com.practica.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;


@Entity
public class Team {
	
	@Id
	private String teamName;
	
	@OneToMany (cascade = CascadeType.ALL)
	private List<Player> players = new ArrayList<>();
	
	public Team() {
	}
	
	public Team(String name) {
		this.teamName = name;
		//this.teamImage = teamImage;
	}

	public String getName() {
		return teamName;
	}

	public void setName(String name) {
		this.teamName = name;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
