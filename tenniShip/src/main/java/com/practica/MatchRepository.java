package com.practica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.practica.model.Match;


public interface MatchRepository extends JpaRepository<Match, Long> {
	@Query(value="SELECT MATCH.*  FROM TOURNAMENT JOIN MATCH",nativeQuery=true)
	List<Match> findAllMatches();
}
