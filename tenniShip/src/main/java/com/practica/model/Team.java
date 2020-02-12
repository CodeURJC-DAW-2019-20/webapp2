package com.practica.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;


@Entity
public class Team {
	
	@Id
	String name;
	

	//private Image teamImage;	
	
	@OneToMany (cascade = CascadeType.ALL)
	private List<Player> players = new ArrayList<>();
		
	@ManyToMany
	private List<Tournament> tournament;
	
	public Team() {
	}
	
	public Team(String name) {
		this.name = name;
		//this.teamImage = teamImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}


	public List<Tournament> getTournament() {
		return tournament;
	}

	public void setTournament(List<Tournament> tournament) {
		this.tournament = tournament;
	}

	
	

}
