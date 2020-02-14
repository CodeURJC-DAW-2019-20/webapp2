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
	String name;

	@ManyToMany
	private List<Team> tournamentTeams = new ArrayList<>();
	
	@OneToMany
	private List<Match> tournamentMatchs = new ArrayList<>();
	
	
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
	
	public List<Team> getTournamentTeams() {
		return tournamentTeams;
	}

	public void setTournamentTeams(List<Team> tournamentTeams) {
		this.tournamentTeams = tournamentTeams;
	}

	public List<Match> getTournamentMatchs() {
		return tournamentMatchs;
	}

	public void setTournamentMatchs(List<Match> tournamentMatchs) {
		this.tournamentMatchs = tournamentMatchs;
	}
	
//	public void setMatch(Team home, Team away, Integer homePoints, Integer awayPoints) {
//		for (Match m : tournamentMatchs) {
//			if (m.getMatchId().equals(home.getName()+away.getName())) {
//				m.setHomePoints(homePoints);
//				m.setAwayPoints(awayPoints);
//				break;
//			}			
//		}
//	}
}
