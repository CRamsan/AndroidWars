package com.cesarandres.aw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameUI extends Stage{

	final OrthographicCamera camera;

	public GameUI () {
		super(new ScreenViewport(new OrthographicCamera()));
		this.camera = (OrthographicCamera) this.getCamera();
		this.camera.setToOrtho(false);
		this.camera.update();		
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

		VerticalGroup g = new VerticalGroup().space(5).reverse().pad(5).fill();
		for (int i = 0; i < 10; i++)
			g.addActor(new TextButton("button " + i, skin));
		g.addActor(new TextButton("longer button", skin));
		Table table = new Table().debug();
		table.add(g);
		table.pack();
		table.setPosition(5, 100);
		this.addActor(table);

		HorizontalGroup h = new HorizontalGroup().space(5).reverse().pad(5).fill();
		for (int i = 0; i < 5; i++)
			h.addActor(new TextButton("button " + i, skin));
		h.addActor(new TextButton("some taller\nbutton", skin));
		table = new Table().debug();
		table.add(h);
		table.pack();
		table.setPosition(130, 100);
		this.addActor(table);
		table.toFront();
	}
	
	public void resize(int width, int height){
		this.getViewport().update(width, height);
	}
}
