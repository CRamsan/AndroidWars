package com.cesarandres.aw;

import java.util.HashSet;
import java.util.Iterator;

public class Player {
	private int ID;
	private int team;
	private HashSet<GameObject> belongings;
	private GameWorld world;

	public Player(int ID, GameWorld stage) {
		this.ID = ID;
		this.belongings = new HashSet<GameObject>();
		this.world = stage;
		
		this.addObject(new GameObject());
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

	public boolean addObject(GameObject object) {
		boolean success = this.belongings.add(object);
		if (success) {
			this.world.addActor(object);
		}
		return success;
	}
	
	public void dispose () {
		Iterator<GameObject> iter = this.belongings.iterator();
		while (iter.hasNext()) {
			iter.next().dispose();
		}	
	}
}
