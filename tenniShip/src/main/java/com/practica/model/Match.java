package com.practica.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Match {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long matchId;
		
	private String home;
	private String away;
	private int homePoints;
	private int awayPoints;
	
	public Match() {
	}
	
	public Match(String home, String away) {
		this.home = home;
		this.away = away;
	}
	
	public Match(String home, String away, int homePoints, int awayPoints) {
		this.home = home;
		this.away = away;
		this.homePoints = homePoints;
		this.awayPoints = awayPoints;
	
	}

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getAway() {
		return away;
	}

	public void setAway(String away) {
		this.away = away;
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