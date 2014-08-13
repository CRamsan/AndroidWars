package com.cesarandres.aw.model;

import java.util.ArrayList;

import com.cesarandres.aw.util.astart.AStarPathFinder;
import com.cesarandres.aw.util.astart.Path;

public class GameInstance {
	private int round;
	private int playerTurn;
	private int maxPlayers;
	private ArrayList<Player> players;
	private TiledMap map;

	//TODO this has to be set to be ignored when serializing
	public static AStarPathFinder pathFinder;
	
	public GameInstance(int width, int height, int maxPlayers) {
		this.players = new ArrayList<Player>();
		this.map = new TiledMap(width, height);
		this.maxPlayers = maxPlayers;
	}

	public int getRound() {
		return round;
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public boolean addPlayer(Player player) {
		if (this.players.size() <= this.maxPlayers) {
			return this.players.add(player);
		} else {
			return false;
		}
	}
	
	public int nextTurn(){
		this.playerTurn++;
		if(this.playerTurn == maxPlayers){
			this.playerTurn = 0;
		}
		return this.playerTurn;
	}
	
	public void addEntity(int x, int y, int player, Entity entity){
		this.map.addMapObject(x, y, entity);
		this.players.get(player).addEntity(entity);
	}
	
	public void removeEntity(int x, int y){
		this.map.removeMapObject(x, y);
	}
	
	public void setTerrain(int x, int y, int value){
		this.map.getTerrain()[x][y] = new Tile(value);		
	}
	
	public void initializePAthFinder(){
		GameInstance.pathFinder = new AStarPathFinder(this.map, false);
	}
	
	public Path createPath(int ox, int oy, int dx, int dy, int maxDistance){
		return GameInstance.pathFinder.findPath(maxDistance, ox, oy, dx, dy);
	}
}
