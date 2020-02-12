package com.practica.model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Match {
	
	@Id
	String matchId;
		
	private Team home;
	private Team away;
	private int homePoints;
	private int awayPoints;
	
	public Match() {
	}
	
	public Match(Team home, Team away) {
		this.matchId = home.getName() + away.getName();
		this.home = home;
		//this.away = away;
	}
	
	public Match(Team home, Team away, int homePoints, int awayPoints) {
		this.matchId = home.getName() + away.getName();
		this.home = home;
		//this.away = away;
		this.homePoints = homePoints;
		this.awayPoints = awayPoints;
	
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public Team getHome() {
		return home;
	}

	public void setHome(Team home) {
		this.home = home;
	}

	/*public Team getAway() {
		return away;
	}

	public void setAway(Team away) {
		this.away = away;
	}*/

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