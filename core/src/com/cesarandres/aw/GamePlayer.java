package com.cesarandres.aw;

import java.util.HashSet;
import java.util.Iterator;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.cesarandres.aw.model.Player;

public class GamePlayer {
	private int ID;
	private int team;
	private HashSet<GameObject> belongings;
	private Player player;
	private GameWorld world;
	
	public GamePlayer(int ID, GameWorld stage) {
		this.ID = ID;
		this.belongings = new HashSet<GameObject>();
		this.player = new Player(ID);
		this.world = stage;
		
		this.addObject(new GameObject(5,5,this), stage);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public HashSet<GameObject> getBelongings() {
		return belongings;
	}

	public boolean addObject(GameObject object, Stage world) {
		boolean success = this.player.getBelongings().add(object.getEntity());
		if (success) {
			this.belongings.add(object);
			world.addActor(object);
		}
		return success;
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

	public void setPlayer(Player player) {
		this.player = player;
	}

	public GameWorld getWorld() {
		return world;
	}

	public void setWorld(GameWorld world) {
		this.world = world;
	}
}
