package com.cesarandres.aw.model;

import java.util.HashSet;

public class Player {
	protected int team;
	protected int money;
	protected HashSet<Entity> belongings;

	public Player(){
		this.belongings = new HashSet<Entity>();
	}
	
	public int getTeam() {
		return team;
	}
	public void setTeam(int team) {
		this.team = team;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public HashSet<Entity> getBelongings() {
		return belongings;
	}
	public void setBelongings(HashSet<Entity> belongings) {
		this.belongings = belongings;
	}
}
