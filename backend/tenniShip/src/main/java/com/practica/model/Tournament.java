package com.practica.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Tournament {

	public interface Basic {
	}

	@JsonView(Basic.class)
	@Id
	private String name;

	private boolean tournamentImage;

	public Tournament() {
	}

	public Tournament(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasImage() {
		return tournamentImage;
	}

	public void setImage(boolean image) {
		this.tournamentImage = image;
	}
}
