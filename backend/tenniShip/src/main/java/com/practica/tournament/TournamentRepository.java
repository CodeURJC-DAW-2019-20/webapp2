package com.practica.tournament;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

	@Query("SELECT coalesce(COUNT(m.id),0) FROM Match m WHERE m.tournament= :t AND (m.homePoints > 0 OR m.awayPoints > 0)")
	public int getPlayedMatchesJQL(Tournament t);

	@Query("SELECT distinct t FROM Tournament t")
	public List<Tournament> getAllTournaments();

	@Query("SELECT distinct t FROM Tournament t WHERE t.name LIKE %:keyword%")
	public List<Team> findSimilarTournaments(String keyword);
}
