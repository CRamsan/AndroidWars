package com.cesarandres.aw;

import java.util.HashSet;
import java.util.Iterator;

import com.cesarandres.aw.model.Player;

public class GamePlayer {
	private int ID;
	private int team;
	private HashSet<GameObject> belongings;
	private Player player;
	private static GameWorld world;
	
	public GamePlayer(int ID, int team, GameWorld stage) {
		this.ID = ID;
		this.team = team;
		this.belongings = new HashSet<GameObject>();
		this.player = new Player(ID, team);
		GamePlayer.world = stage;
	}

	public int getID() {
		return ID;
	}

	public int getTeam() {
		return team;
	}

	public HashSet<GameObject> getBelongings() {
		return belongings;
	}

	//TODO FIx return value
	public boolean addObject(int x, int y, GameObject object, GameWorld world) {
		this.player.addEntity(object.getEntity());
		this.belongings.add(object);
		world.addActor(object);
		return true;
	}
	
	public void dispose () {
		Iterator<GameObject> iter = this.belongings.iterator();
		while (iter.hasNext()) {
			iter.next().dispose();
		}	
	}

	public Player getPlayer() {
		return player;
	}

	public GameWorld getWorld() {
		return world;
	}
}
