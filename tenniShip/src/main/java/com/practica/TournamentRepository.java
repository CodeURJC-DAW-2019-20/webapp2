package com.practica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.practica.model.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, String> {
	
	
}
