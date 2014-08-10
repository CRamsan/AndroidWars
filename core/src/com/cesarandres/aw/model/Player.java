package com.cesarandres.aw.model;

import java.util.HashSet;

public class Player {
	protected int ID;
	protected int money;
	protected HashSet<Entity> belongings;

	public Player(int ID){
		this.ID = ID;
		this.belongings = new HashSet<Entity>();
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
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
