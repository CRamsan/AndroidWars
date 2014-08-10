package com.cesarandres.aw.model;

import com.cesarandres.aw.GameObject;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class TiledMap {
	private int width;
	private int height;
	
	private Tile[][] terrain;
	private Table<Integer, Integer, GameObject> mapObjects;
	
	public TiledMap(int width, int height){
		this.width = width;
		this.height = height;
		this.terrain = new Tile[width][height];
		this.mapObjects = HashBasedTable.create();
	}
	
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

	public Table<Integer, Integer, GameObject> getMapObjects() {
		return mapObjects;
	}

	public void setMapObjects(Table<Integer, Integer, GameObject> mapObjects) {
		this.mapObjects = mapObjects;
	}
	
	public boolean isEmpty(int x, int y) {
		return !this.mapObjects.contains(x, y);
	}

	public void addMapObject(int x, int y, GameObject object) {
		this.mapObjects.put(x, y, object);
	}

	public GameObject getMapObject(int x, int y, GameObject object) {
		return this.mapObjects.get(x, y);
	}

	public void removeMapObject(int x, int y) {
		this.mapObjects.remove(x, y);
	}
}
