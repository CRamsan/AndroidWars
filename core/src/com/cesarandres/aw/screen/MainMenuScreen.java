package com.cesarandres.aw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cesarandres.aw.AndroidWars;

public class MainMenuScreen implements Screen {

	private Stage stage;
	private AndroidWars aw;
	private TextureRegion background;
	private SpriteBatch batch;

	public MainMenuScreen(final AndroidWars aw) {
		this.aw = aw;

		this.stage = new Stage();
		this.stage.setViewport(new ScreenViewport(new OrthographicCamera()));

		Gdx.input.setInputProcessor(this.stage);

		Table window = new Table(AndroidWars.skin);
		window.setFillParent(true);
		window.add(new TextButton("Campaing", AndroidWars.skin));
		window.row();
		window.add(new TextButton("Start Game", AndroidWars.skin));

		this.stage.addActor(window);

		Texture texture = new Texture(Gdx.files.internal("Intro.png"));
		this.background = new TextureRegion(texture);

		this.batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Viewport viewPort = this.stage.getViewport();
		batch.setProjectionMatrix(viewPort.getCamera().combined);
		batch.begin();
		batch.draw(this.background, 0, 0, viewPort.getViewportWidth(), viewPort.getViewportHeight());
		batch.end();

		this.stage.act(Math.min(delta, 1 / 30f));
		this.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		this.stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		this.stage.dispose();
		this.background.getTexture().dispose();
		this.batch.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}
}