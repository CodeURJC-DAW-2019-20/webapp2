package com.practica.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String playerName;
	//private Image playerImage;
	

	public Player() {
	}

	public Player(String name/*, Image playerImage*/) {
		this.playerName = name;
		//this.playerImage = playerImage;
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

//	public Image getPlayerImage() {
//		return playerImage;
//	}
//
//	public void setPlayerImage(Image playerImage) {
//		this.playerImage = playerImage;
//	}
//
//	@Override
//	public String toString() {
//		return "Player [id=" + id + ", name=" + name + ", playerImage=" + playerImage + "]";
//	}
	
	
}
