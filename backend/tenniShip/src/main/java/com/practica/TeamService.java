package com.practica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;

@Service
public class TeamService {
	
	@Autowired
	private TeamRepository teamRepository;
	
	public Optional<Team> findById(String team) {
		return teamRepository.findById(team);
	}
	
	public void save (Team team) {
		teamRepository.save(team);
	}
	
	public List<Tournament> getTournaments(Team team) {
		return teamRepository.getTournaments(team);
	}
	
	public List<String> getTournamentsName(Team team) {
		return teamRepository.getTournamentsName(team);
	}
	
	public int getWonGroupMatches(Tournament tournament, Team team, String phase) {
		return teamRepository.getWonGroupMatches(tournament, team, phase);
	}
	
	public int getWonGroupPointsPlayingHome(Tournament tournament, Team team, String phase) {
		return teamRepository.getWonGroupPointsPlayingHome(tournament, team, phase);
	}
	
	public int getWonGroupPointsPlayingAway(Tournament tournament, Team team, String phase) {
		return teamRepository.getWonGroupPointsPlayingAway(tournament, team, phase);
	}
	
	public int getMatchesWon(String team) {
		return teamRepository.getMatchesWon(team);
	}
	
	public int getMatchesLost(String team) {
		return teamRepository.getMatchesLost(team);
	}
	
	public List<Match> getRecentMatches(Team team) {
		return teamRepository.getRecentMatches(team);
	}
	
	public Team getTeamToAdd(String team) {
		return teamRepository.getTeamToAdd(team);
	}
	
	public List<Team> getAllTeams() {
		return teamRepository.getAllTeams();
	}
	
	public List<Team> findSimilarTeams(String team) {
		return teamRepository.findSimilarTeams(team);
	}
}
