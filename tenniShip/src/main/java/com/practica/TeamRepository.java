package com.practica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.practica.model.Team;

public interface TeamRepository extends JpaRepository<Team, String> {
	@Query("select t.name from Team t")
	List<String> findAllTeams();
}
