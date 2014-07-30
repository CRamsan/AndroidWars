package com.cesarandres.aw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class AndroidWars extends ApplicationAdapter {

	private GameWorld match;
	private GameUI ui;
	
	@Override
	public void create() {				
		this.match = new GameWorld();
		this.ui = new GameUI();
		
		Gdx.input.setInputProcessor(ui);
		Gdx.input.setInputProcessor(match);
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
