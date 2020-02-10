package com.practica;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Player;;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
