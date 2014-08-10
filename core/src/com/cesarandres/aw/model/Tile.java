package com.cesarandres.aw.model;

public class Tile  {
	private int cost;
	private Building building;
	private Unit unit;
	
	public Tile(int cost){
		this.cost = cost;
	}
	
	public Building getBuilding() {
		return building;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}

}
