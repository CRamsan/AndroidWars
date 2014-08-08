package com.cesarandres.aw.model;

public class Entity {
	private int x; 
	private int y;
	private Player parent;
	
	public Entity(int x, int y, Player parent){
		this.x = x;
		this.y = y;
		this.setParent(parent);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public Player getParent() {
		return parent;
	}

	public void setParent(Player parent) {
		this.parent = parent;
	}

}
