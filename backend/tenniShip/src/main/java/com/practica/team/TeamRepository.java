package com.practica.team;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

	@Query("SELECT coalesce(COUNT(m.id), 0) FROM Match m WHERE  "
			+ "((m.team1 = :tm AND m.homePoints > m.awayPoints) OR (m.team2 = :tm AND m.homePoints < m.awayPoints))")
	public int getMatchesWon(Team tm);

	@Query("SELECT coalesce(COUNT(m.id), 0) FROM Match m WHERE  "
			+ "((m.team1 = :tm AND m.homePoints < m.awayPoints) OR (m.team2 = :tm AND m.homePoints > m.awayPoints))")
	public int getMatchesLost(Team tm);

	@Query("SELECT DISTINCT m " + "FROM Match m JOIN Team t "
			+ "ON (m.team1.teamName=t.teamName OR m.team2.teamName=t.teamName) "
			+ "WHERE (((m.team1 = :team) OR (m.team2 = :team)) AND ( (m.homePoints > 0) OR (m.awayPoints > 0) ))")
	public List<Match> getRecentMatches(Team team);

	@Query("SELECT distinct team FROM Team team ")
	public List<Team> getAllTeams();

	@Query("SELECT distinct t FROM Team t WHERE t.teamName LIKE %:keyword%")
	public List<Team> findSimilarTeams(String keyword);

	@Query("SELECT DISTINCT m " + "FROM Match m JOIN Team t "
			+ "ON (m.team1.teamName=t.teamName OR m.team2.teamName=t.teamName) "
			+ "WHERE (((m.team1 = :team) OR (m.team2 = :team)) AND ( (m.homePoints > 0) OR (m.awayPoints > 0) ))")
	public Page<Match> getRecentMatchesPaginated(Team team, Pageable pageable);

	@Query("SELECT distinct t FROM Match m JOIN m.tournament t " + "WHERE m.team1 = :team OR m.team2 = :team")
	public Page<Tournament> getTournamentsPaginated(Team team, Pageable pageable);
}
