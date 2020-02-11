package com.practica;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practica.model.Team;

public interface TeamRepository extends JpaRepository<Team, String> {

}
