package com.cesarandres.aw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class AndroidWars extends ApplicationAdapter {

	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private AssetManager assetManager;
	private GameWorld match;
	
	@Override
	public void create() {		
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false);
		this.camera.update();
		
		this.match = new GameWorld(new ScreenViewport(camera), camera);
		Gdx.input.setInputProcessor(match);

		this.assetManager = new AssetManager();
		this.assetManager.setLoader(TiledMap.class, new TmxMapLoader(
				new InternalFileHandleResolver()));
		this.assetManager.load("desert.tmx",
				TiledMap.class);
		this.assetManager.finishLoading();
		this.map = assetManager.get("desert.tmx");
		this.renderer = new OrthogonalTiledMapRenderer(map);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.camera.update();
		this.renderer.setView(camera);
		this.renderer.render();
	}
	
	@Override
	public void resize(int width, int height) {
		this.match.getViewport().update(width, height);
	}
}
