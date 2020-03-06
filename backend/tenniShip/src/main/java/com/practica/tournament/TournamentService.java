package com.practica.tournament;

import java.util.List;
import java.util.Optional;

import com.practica.security.UserComponent;
import com.practica.team.TeamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.practica.match.MatchService;
import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;

@Service
public class TournamentService {

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private TeamService teamService;

	@Autowired
	private UserComponent userComponent;

	@Autowired
	private MatchService matchService;

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

	public ResponseEntity<Match> putTheMatch(String tournament, Match newMatch, boolean isAdmin) {
		Optional<Tournament> t = findById(tournament);
		Optional<Team> home = teamService.findById(newMatch.getTeam1().getName());
		Optional<Team> away = teamService.findById(newMatch.getTeam2().getName());
		if (t.isPresent() && home.isPresent() && away.isPresent()) {
			if (isAdmin || getTeams(t.get()).contains(teamService.findById(userComponent.getTeam()).get())) {
				if (newMatch.getHomePoints() == 3 ^ newMatch.getAwayPoints() == 3) { // XOR
					Match match = matchService.findMatch(home.get(), away.get(), t.get()).get(0);
					if (match != null) {
						matchService.getOne(match.getId()).setHomePoints(newMatch.getHomePoints());
						matchService.getOne(match.getId()).setAwayPoints(newMatch.getAwayPoints());

						matchService.save(match);
						return new ResponseEntity<>(match, HttpStatus.CREATED);
					}
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				} else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
