package com.cesarandres.aw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class GameDialog {
	
	public GameDialog(Stage stage){
		Texture texture1 = new Texture(Gdx.files.internal("badlogic.jpg"));
		TextureRegion image = new TextureRegion(texture1);
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

		Button buttonMulti = new TextButton("Multi\nLine\nToggle", skin, "toggle");
		Button imgButton = new Button(new Image(image), skin);
		Button imgToggleButton = new Button(new Image(image), skin, "toggle");
		
		Window window = new Window("Dialog", skin);
		window.getButtonTable().add(new TextButton("X", skin)).height(window.getPadTop());
		window.setPosition(0, 0);
		window.defaults().spaceBottom(10);
		window.row().fill().expandX();
		window.add(buttonMulti);
		window.add(imgButton);
		window.add(imgToggleButton);
		window.pack();

		stage.addActor(window);
	}

}
