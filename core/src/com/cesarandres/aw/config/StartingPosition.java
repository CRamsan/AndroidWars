package com.cesarandres.aw.config;

import java.util.ArrayList;
import java.util.List;

public class StartingPosition {

	private int player;
	private List<Position> positions = new ArrayList<Position>();

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

}