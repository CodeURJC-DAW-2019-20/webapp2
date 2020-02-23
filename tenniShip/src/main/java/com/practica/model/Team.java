package com.practica.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Team {
	
	@Id
	private String teamName;
	
	private boolean teamImage;
	
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
	
	public boolean hasImage() {
		return teamImage;
	}

	public void setImage(boolean image) {
		this.teamImage = image;
	}

}
