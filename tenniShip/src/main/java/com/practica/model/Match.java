package com.practica.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Match {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
		
	private int homePoints;
	private int awayPoints;
	private String type; 
	/*
		groupA : A
		groupB : B
		groupC : C
		groupD : D
		groupE : E
		groupF : F
		Round of 8: X --last 8 teams
		Round of 4: Y --last 4 teams
		Round of 2: Z --last 2 teams
	*/
	
	@ManyToOne
	private Team team1;

	@ManyToOne
	private Team team2;

	@ManyToOne
	Tournament tournament;	
	
	public Match() {
	}
	
	public Match(int homePoints, int awayPoints, String type) {
		this.homePoints = homePoints;
		this.awayPoints = awayPoints;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public int getHomePoints() {
		return homePoints;
	}

	public void setHomePoints(int homePoints) {
		this.homePoints = homePoints;
	}

	public int getAwayPoints() {
		return awayPoints;
	}

	public void setAwayPoints(int awayPoints) {
		this.awayPoints = awayPoints;
	}
}