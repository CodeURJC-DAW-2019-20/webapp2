package com.practica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;


public interface MatchRepository extends JpaRepository<Match, Long> {
	
	@Query("SELECT distinct m FROM Match m WHERE m.tournament = :tournament AND (m.team1 = :team OR m.team2 = :team)")
	List<Match> findMatches(Team team, Tournament tournament);
	
	@Query("SELECT distinct m FROM Match m WHERE m.tournament = :tournament AND m.team1 = :team1 AND m.team2 = :team2")
	List<Match> findMatch(Team team1, Team team2, Tournament tournament);

}
