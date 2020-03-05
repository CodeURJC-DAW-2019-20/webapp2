package com.practica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;

@Service
public class MatchService {

	@Autowired
	private MatchRepository matchRepository;

	public void save(Match match) {
		matchRepository.save(match);
	}

	public void delete(Match match) {
		matchRepository.delete(match);
	}

	public List<Match> findMatches(Team team, Tournament tournament) {
		return matchRepository.findMatches(team, tournament);
	}

	public List<Match> findMatch(Team team1, Team team2, Tournament tournament) {
		return matchRepository.findMatch(team1, team2, tournament);
	}

	public Match getOne(Long id) {
		return matchRepository.getOne(id);
	}
}
