package com.practica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;

public interface TeamRepository extends JpaRepository<Team, String> {

	@Query("SELECT distinct t FROM Match m JOIN m.tournament t " +
			"WHERE m.team1 = :team OR m.team2 = :team")
	public List<Tournament> getTournaments(Team team);
	
	@Query(value="SELECT COUNT(partido.id) FROM partido JOIN Team " +
			"ON (partido.team1_team_name=Team.team_name OR partido.team2_team_name=Team.team_name) " +
			"WHERE (((partido.team1_team_name=:n) AND (partido.home_points>partido.away_points)) " +
			"OR ((partido.team2_team_name=:n) AND (partido.home_points<partido.away_points)))", nativeQuery = true )
	public int getMatchesWon(@Param("n") String n);
	
	@Query(value="SELECT COUNT(partido.id) FROM partido JOIN Team " +
			"ON (partido.team1_team_name=Team.team_name OR partido.team2_team_name=Team.team_name) " +
			"WHERE (((partido.team1_team_name=:n) AND (partido.home_points<partido.away_points)) " +
			"OR ((partido.team2_team_name=:n) AND (partido.home_points>partido.away_points)))", nativeQuery = true )
	public int getMatchesLost(@Param("n") String n); 

	@Query("SELECT DISTINCT m " +
			"FROM Match m JOIN Team t " +
			"ON (m.team1.teamName=t.teamName OR m.team2.teamName=t.teamName) " +
			"WHERE ((m.team1 = :team) OR (m.team2 = :team) AND ( (m.homePoints > 0) OR (m.awayPoints > 0) ))")
	public List<Match> getRecentMatches(Team team);
	
	@Query(value="SELECT Team.* FROM Team WHERE teamName = : n ",nativeQuery= true)
	public Team getTeamToAdd(@Param("n") String n);
}
