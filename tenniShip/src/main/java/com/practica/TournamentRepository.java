package com.practica;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.practica.model.Match;
import com.practica.model.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, String> {

//	@Query(value="SELECT * FROM TOURNAMENT_TOURNAMENT_TEAMS",nativeQuery=true)
//	List<Object (String,String)> findTournament_Teams();
}
