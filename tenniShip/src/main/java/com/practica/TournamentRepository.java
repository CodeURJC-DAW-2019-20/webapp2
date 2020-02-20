package com.practica;

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
	
	@Query("SELECT distinct m FROM Match m WHERE m.tournament = :t AND m.homePoints = 0 AND m.awayPoints = 0 AND (m.team1 = :tm OR m.team2 = :tm)")
	public List<Match> getNextMatches(Tournament t, Team tm);
	
	@Query("SELECT distinct team FROM Match m, Team team WHERE (m.team1 = team OR m.team2 = team) AND  m.tournament = :t")
	public List<Team> getTeams(Tournament t);
	
	@Query("SELECT distinct team FROM Match m, Team team WHERE (m.team1 = team OR m.team2 = team) AND  m.tournament = :t AND m.type = :g")
	public List<Team> getPhaseTeams(Tournament t, String g);
	
	@Query("SELECT COUNT(m.id) FROM Match m WHERE m.tournament= :t AND (m.homePoints > 0 OR m.awayPoints > 0)")
    public int getPlayedMatchesJQL(Tournament t);
	
	@Query(value="SELECT COUNT(partido.id) FROM partido JOIN tournament ON(tournament_name=tournament.name) WHERE tournament_name=:t AND (home_points>0 OR away_points>0)",nativeQuery= true)
    public int getPlayedMatches(@Param("t") String t);
	
	//SELECT * FROM team JOIN partido ON ((team.name=partido.team1_name) OR (team.name=partido.team2_name)) JOIN Tournament ON (partido.tournament_name=tournament.name) WHERE team.name='Spain'
}
