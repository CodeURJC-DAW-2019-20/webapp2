package com.practica.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica.model.Match;
import com.practica.model.Player;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.team.TeamRestController.TeamFileData;

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
	
	public int getMatchesWon(Team team) {
		return teamRepository.getMatchesWon(team);
	}
	
	public int getMatchesLost(Team team) {
		return teamRepository.getMatchesLost(team);
	}
	
	public List<Match> getRecentMatches(Team team) {
		return teamRepository.getRecentMatches(team);
	}
	
	public List<Team> getAllTeams() {
		return teamRepository.getAllTeams();
	}
	
	public List<Team> findSimilarTeams(String team) {
		return teamRepository.findSimilarTeams(team);
	}
	
	public Page<Match> getPagesInMatches(Team team, Pageable page, int end){
		
		page = PageRequest.of(0, end);
		
		return teamRepository.getRecentMatchesPaginated(team,page);
	}
	
	public List<Match> getListMatches(Page<Match> pages) {
        List<Match> pageMatches = new ArrayList<>();
        for (Match m : pages) {
            pageMatches.add(m);
        }

        return pageMatches;
    }
	
	public Page<Tournament> getPagesInTournaments(Team team,Pageable page, int end){
		
		page = PageRequest.of(0, end);
		
		return teamRepository.getTournamentsPaginated(team, page);
	}
	
	public List<Tournament> getListTournaments(Page<Tournament> pages){
		List<Tournament> pageTournaments = new ArrayList<>();
		for(Tournament t : pages) {
			pageTournaments.add(t);
		}
		
		return pageTournaments;
	}
	
	
	public TeamFileData teamProfile(Team team) {		
		
		double percentageMatchesLost = 0.0;
		double percentageMatchesWon = 0.0;
		double totalMatches = 0.0;

		
		percentageMatchesLost = teamRepository.getMatchesLost(team);
		percentageMatchesWon = teamRepository.getMatchesWon(team);

		totalMatches = percentageMatchesLost + percentageMatchesWon;

		percentageMatchesLost = (percentageMatchesLost / totalMatches) * 100;
		percentageMatchesWon = (percentageMatchesWon / totalMatches) * 100;
		
		List<String> tournamentsList = teamRepository.getTournamentsName(team);

		List<Player> players = team.getPlayers();
		
		return new TeamFileData(team.getName(),tournamentsList,team.hasTeamImage(),
				percentageMatchesLost,percentageMatchesWon,players);
	}
}
