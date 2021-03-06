package com.practica.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Player {

	public interface Basic {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@JsonView(Basic.class)
	private String playerName;
	private boolean playerImage;

	public Player() {
	}

	public Player(String name) {
		this.playerName = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return playerName;
	}

	public void setName(String name) {
		this.playerName = name;
	}

	public boolean hasPlayerImage() {
		return playerImage;
	}

	public void setPlayerImage(boolean image) {
		this.playerImage = image;
	}
}
