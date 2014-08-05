package com.cesarandres.aw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameUI extends Stage{

	final OrthographicCamera camera;
	
	private Button nextTurnButton;
	private Table uiTable;
	
	public GameUI () {
		super(new ScreenViewport(new OrthographicCamera()));
		this.camera = (OrthographicCamera) this.getCamera();
		this.camera.setToOrtho(false);
		this.camera.update();		
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		this.uiTable = new Table();
		this.uiTable.setFillParent(true);
		
		this.nextTurnButton = new TextButton("End Turn", skin);
		this.nextTurnButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Next Turn");
			}
		});

		this.uiTable.add(this.nextTurnButton).pad(5).top().right();
		
		this.addActor(this.uiTable);
	}
	
	public void resize(int width, int height){
		this.getViewport().update(width, height, true);
		this.uiTable.top().right();
 	}
}
