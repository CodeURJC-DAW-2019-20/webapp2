package com.practica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;

public interface TeamRepository extends JpaRepository<Team, String> {

	@Query("SELECT distinct t FROM Match m JOIN m.tournament t WHERE m.team1 = :team OR m.team2 = :team")
	public List<Tournament> getTournaments(Team team);

}
