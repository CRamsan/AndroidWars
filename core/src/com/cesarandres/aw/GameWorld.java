package com.cesarandres.aw;

import java.util.HashSet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameWorld extends Stage{

	private OrthographicCamera camera;

	private TiledMap map;
	private TiledMapRenderer renderer;
	private AssetManager assetManager;

	private HashSet<Player> players;
	
	public GameWorld (OrthographicCamera camera) {
		super(new ScreenViewport(camera));
		this.camera = camera;
		this.camera.setToOrtho(false);
		this.camera.update();
			
		this.assetManager = new AssetManager();
		this.assetManager.setLoader(TiledMap.class, new TmxMapLoader(
				new InternalFileHandleResolver()));
		this.assetManager.load("desert.tmx",
				TiledMap.class);
		this.assetManager.finishLoading();
		this.map = assetManager.get("desert.tmx");
		this.renderer = new OrthogonalTiledMapRenderer(map);
	}

	public boolean addPlayer(Player player){
		return this.players.add(player);
	}
	
	@Override
	public void draw(){
		this.camera.update();
		this.renderer.setView(camera);
		this.renderer.render();
		super.draw();
	}
			
	public void resize(int width, int height){
		this.getViewport().update(width, height);
	}
}
