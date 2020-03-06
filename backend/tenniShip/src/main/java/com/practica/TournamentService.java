package com.practica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;

@Service
public class TournamentService {

	@Autowired
	private TournamentRepository tournamentRepository;

	public Optional<Tournament> findById(String tournament) {
		return tournamentRepository.findById(tournament);
	}

	public void save(Tournament tournament) {
		tournamentRepository.save(tournament);
	}

	public void delete(Tournament tournament) {
		tournamentRepository.delete(tournament);
	}

	public List<Match> getMatches(Tournament tournament) {
		return tournamentRepository.getMatches(tournament);
	}

	public List<Match> getPhaseMatches(Tournament tournament, String phase) {
		return tournamentRepository.getPhaseMatches(tournament, phase);
	}

	public List<Match> getNextMatches(Tournament tournament, Team team) {
		return tournamentRepository.getNextMatches(tournament, team);
	}

	public List<Team> getTeams(Tournament tournament) {
		return tournamentRepository.getTeams(tournament);
	}

	public List<Team> getPhaseTeams(Tournament tournament, String phase) {
		return tournamentRepository.getPhaseTeams(tournament, phase);
	}

	public int getPlayedMatchesJQL(Tournament tournament) {
		return tournamentRepository.getPlayedMatchesJQL(tournament);
	}

	public int getPlayedMatches(String tournament) {
		return tournamentRepository.getPlayedMatches(tournament);
	}

	public List<Tournament> getAllTournaments() {
		return tournamentRepository.getAllTournaments();
	}

	public List<Team> findSimilarTournaments(String tournament) {
		return tournamentRepository.findSimilarTournaments(tournament);
	}
}
