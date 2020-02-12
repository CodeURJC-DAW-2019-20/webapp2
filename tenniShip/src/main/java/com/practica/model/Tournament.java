package com.practica.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
public class Tournament {
	@Id
	//@GeneratedValue(strategy= GenerationType.AUTO)
	String name;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
//	In this part we should take into account
//	the possibility of a team being erased if
//	that team does not participate in more
//	tournaments
	@ManyToMany
	private List<Team> tournamentTeams = new ArrayList<>();
	
	
	public Tournament() {		
	}
	
	public Tournament(String name) {
		this.name = name;
	}

	public List<Team> getTournamentTeams() {
		return tournamentTeams;
	}

	public void setTournamentTeams(List<Team> tournamentTeams) {
		this.tournamentTeams = tournamentTeams;
	}
	
	
	
}
