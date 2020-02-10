package com.practica;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
