package com.practica;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.practica.model.Match;
import com.practica.model.Tournament;
import com.practica.model.Team;

public interface TournamentRepository extends JpaRepository<Tournament, String> {

	@Query("SELECT distinct m FROM Match m WHERE m.tournament = :t")
	public List<Match> getMatches(Tournament t);
	
	@Query("SELECT distinct m FROM Match m WHERE m.tournament = :t AND m.type = :g")
	public List<Match> getPhaseMatches(Tournament t, String g);
	
	@Query("SELECT distinct team FROM Match m, Team team WHERE (m.team1 = team OR m.team2 = team) AND  m.tournament = :t")
	public List<Team> getTeams(Tournament t);
	
	@Query("SELECT distinct team FROM Match m, Team team WHERE (m.team1 = team OR m.team2 = team) AND  m.tournament = :t AND m.type = :g")
	public List<Team> getPhaseTeams(Tournament t, String g);
	
//	@Query(value = "SELECT * FROM tenniship.partido JOIN tenniship.tournament WHERE (tenniship.partido.home_points=tenniship.partido.away_points AND tenniship.partido.home_points=0)",nativeQuery = true)
//	public int getNumberOfPlayedMatches(Tournament t);
	
	@Query(value="SELECT COUNT(partido.id) FROM partido JOIN tournament ON(tournament_name=tournament.name) WHERE tournament_name=:t AND (home_points>0 OR away_points>0)",nativeQuery= true)
    public int getPlayedMatches(@Param("t") String t);
}
