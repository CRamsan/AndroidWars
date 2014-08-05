package com.cesarandres.aw.model;

public class TiledMap {
	public int width;
	public int height;
	
	public Tile[][] terrain;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Tile[][] getTerrain() {
		return terrain;
	}

	public void setTerrain(Tile[][] terrain) {
		this.terrain = terrain;
	}
}
