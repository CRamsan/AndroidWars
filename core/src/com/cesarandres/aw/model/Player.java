package com.cesarandres.aw.model;

import java.util.HashSet;

public class Player {
	protected int ID;
	protected int team;
	protected int money;
	protected HashSet<Entity> belongings;

	public Player(int ID, int team){
		this.ID = ID;
		this.team = team;
		this.belongings = new HashSet<Entity>();
	}
	
	public int getID() {
		return ID;
	}
	public int getMoney() {
		return money;
	}
	public int getTeam() {
		return team;
	}
	public boolean addEntity(Entity entity) {
		return belongings.add(entity);
	}
	public boolean removeEntity(Entity entity) {
		return belongings.remove(entity);
	}	
}
