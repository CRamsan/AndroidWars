package com.cesarandres.aw.config;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

public class ConfigurationFile {

	@SerializedName("map_file")
	private String mapFile;

	@SerializedName("max_players")
	private Integer maxPlayers;

	@SerializedName("starting_positions")
	private List<StartingPosition> startingPositions = new ArrayList<StartingPosition>();

	public Integer getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(Integer maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public List<StartingPosition> getStartingPositions() {
		return startingPositions;
	}

	public void setStartingPositions(List<StartingPosition> startingPositions) {
		this.startingPositions = startingPositions;
	}

	public String getMapFile() {
		return mapFile;
	}

	public void setMapFile(String mapFile) {
		this.mapFile = mapFile;
	}
	
	public static ConfigurationFile getGameConfiguration(FileHandle file){
		Gson gson = new Gson();
		try {
			return gson.fromJson(file.readString(), ConfigurationFile.class);
		} catch (JsonIOException e) {
			e.printStackTrace();
			return null;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}
