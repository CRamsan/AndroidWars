package com.cesarandres.aw.model;

import java.util.HashSet;

public class GameInstance {
	private int round;
	private int playerTurn;
	private HashSet<Player> players;
	
	public GameInstance(){
		this.players = new HashSet<Player>();
	}
	
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getPlayerTurn() {
		return playerTurn;
	}
	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}
	public HashSet<Player> getPlayers() {
		return players;
	}
	public void setPlayers(HashSet<Player> players) {
		this.players = players;
	}
}
