package com.practica.team;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;

public interface TeamRepository extends JpaRepository<Team, String> {

	@Query("SELECT distinct t FROM Match m JOIN m.tournament t " + "WHERE m.team1 = :team OR m.team2 = :team")
	public List<Tournament> getTournaments(Team team);

	@Query("SELECT distinct t.name FROM Match m JOIN m.tournament t " + "WHERE m.team1 = :team OR m.team2 = :team")
	public List<String> getTournamentsName(Team team);

	@Query("SELECT coalesce(COUNT(m.id), 0) FROM Match m WHERE m.tournament = :t AND m.type = :g "
			+ "AND ((m.team1 = :tm AND m.homePoints > m.awayPoints) OR (m.team2 = :tm AND m.homePoints < m.awayPoints))")
	public int getWonGroupMatches(Tournament t, Team tm, String g);

	@Query("SELECT coalesce(sum(m.homePoints), 0) FROM Match m WHERE m.tournament = :t AND m.type = :g AND m.team1 = :tm ")
	public int getWonGroupPointsPlayingHome(Tournament t, Team tm, String g);

	@Query("SELECT coalesce(sum(m.awayPoints), 0) FROM Match m WHERE m.tournament = :t AND m.type = :g AND m.team2 = :tm ")
	public int getWonGroupPointsPlayingAway(Tournament t, Team tm, String g);

	@Query(value = "SELECT coalesce(COUNT(partido.id), 0) FROM partido JOIN Team "
			+ "ON (partido.team1_team_name=Team.team_name OR partido.team2_team_name=Team.team_name) "
			+ "WHERE (((partido.team1_team_name=:n) AND (partido.home_points>partido.away_points)) "
			+ "OR ((partido.team2_team_name=:n) AND (partido.home_points<partido.away_points)))", nativeQuery = true)
	public int getMatchesWon(@Param("n") String n);

	@Query(value = "SELECT coalesce(COUNT(partido.id), 0) FROM partido JOIN Team "
			+ "ON (partido.team1_team_name=Team.team_name OR partido.team2_team_name=Team.team_name) "
			+ "WHERE (((partido.team1_team_name=:n) AND (partido.home_points<partido.away_points)) "
			+ "OR ((partido.team2_team_name=:n) AND (partido.home_points>partido.away_points)))", nativeQuery = true)
	public int getMatchesLost(@Param("n") String n);

	@Query("SELECT DISTINCT m " + "FROM Match m JOIN Team t "
			+ "ON (m.team1.teamName=t.teamName OR m.team2.teamName=t.teamName) "
			+ "WHERE (((m.team1 = :team) OR (m.team2 = :team)) AND ( (m.homePoints > 0) OR (m.awayPoints > 0) ))")
	public List<Match> getRecentMatches(Team team);

	@Query(value = "SELECT Team.* FROM Team WHERE team_name = : n ", nativeQuery = true)
	public Team getTeamToAdd(@Param("n") String n);

	@Query(value = "SELECT team.* FROM Team", nativeQuery = true)
	public List<Team> getAllTeams();

	@Query(value = "SELECT team.* FROM Team WHERE team_name LIKE %:keyword%", nativeQuery = true)
	public List<Team> findSimilarTeams(@Param("keyword") String keyword);
	
	@Query("SELECT DISTINCT m " +
			"FROM Match m JOIN Team t " +
			"ON (m.team1.teamName=t.teamName OR m.team2.teamName=t.teamName) " +
			"WHERE (((m.team1 = :team) OR (m.team2 = :team)) AND ( (m.homePoints > 0) OR (m.awayPoints > 0) ))")
	public Page<Match> getRecentMatchesPaginated(Team team, Pageable pageable);
	
	@Query("SELECT distinct t FROM Match m JOIN m.tournament t " + "WHERE m.team1 = :team OR m.team2 = :team")
	public Page<Tournament> getTournamentsPaginated(Team team, Pageable pageable);
}
