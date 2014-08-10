package com.cesarandres.aw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cesarandres.aw.config.ConfigurationFile;

public class AndroidWars extends ApplicationAdapter {

	private GameWorld match;
	private OrthoCamController controller;
	private GameUI ui;
			
	@Override
	public void create() {				
		
		FileHandle file = Gdx.files.internal("map_config.json");
		ConfigurationFile config = ConfigurationFile.getGameConfiguration(file);
		
		OrthographicCamera camera = new OrthographicCamera();

		this.ui = new GameUI();
		this.match = new GameWorld(camera, config);
		this.controller = new OrthoCamController(camera, this.match);

		InputMultiplexer multiplexer = new InputMultiplexer();
		
		multiplexer.addProcessor(ui);
		multiplexer.addProcessor(match);
		multiplexer.addProcessor(controller);

		Gdx.input.setInputProcessor(multiplexer);
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    this.match.act(Gdx.graphics.getDeltaTime());
	    this.match.draw();
	    this.ui.act(Gdx.graphics.getDeltaTime());
	    this.ui.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		this.match.resize(width, height);
		this.ui.resize(width, height);
	}
}
