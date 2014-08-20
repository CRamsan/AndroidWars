package com.cesarandres.aw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.cesarandres.aw.screen.MainMenuScreen;

public class AndroidWars extends Game {

	public static Skin skin;

	@Override
	public void create() {
		AndroidWars.skin = new Skin(Gdx.files.internal("uiskin.json"));

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		AndroidWars.skin.dispose();
	}
	
	@Override
	public void render() {
		super.render();
	}
}
