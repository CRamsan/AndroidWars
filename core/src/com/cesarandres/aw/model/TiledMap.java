package com.cesarandres.aw.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class TiledMap {
	private int width;
	private int height;
	
	private Tile[][] terrain;
	private Table<Integer, Integer, Entity> mapObjects;
	
	public TiledMap(int width, int height){
		this.width = width;
		this.height = height;
		this.terrain = new Tile[width][height];
		this.mapObjects = HashBasedTable.create();
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Tile[][] getTerrain() {
		return terrain;
	}
	
	public boolean isEmpty(int x, int y) {
		return !this.mapObjects.contains(x, y);
	}

	public void addMapObject(int x, int y, Entity object) {
		this.mapObjects.put(x, y, object);
	}

	public Entity getMapObject(int x, int y) {
		return this.mapObjects.get(x, y);
	}

	public void removeMapObject(int x, int y) {
		this.mapObjects.remove(x, y);
	}
}
