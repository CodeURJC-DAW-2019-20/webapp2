package com.practica.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	String name;
	

	//private Image teamImage;	
	
	@OneToMany (cascade = CascadeType.ALL)
	private List<Player> players = new ArrayList<>();
	
	@OneToMany (cascade = CascadeType.ALL)
	private Map<String, List<Match>> matchs = new ConcurrentHashMap<>();
	
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

	public List<Match> getMatchs(String t) {
		return matchs.get(t);
	}

	public void setMatch(String t, Match m) {
		this.matchs.get(t).add(m);
	}

	public List<Tournament> getTournament() {
		return tournament;
	}

	public void setTournament(List<Tournament> tournament) {
		this.tournament = tournament;
	}

	
	

}
