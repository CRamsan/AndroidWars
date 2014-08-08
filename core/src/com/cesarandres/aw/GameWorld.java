package com.cesarandres.aw;

import java.util.HashSet;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cesarandres.aw.model.GameInstance;

public class GameWorld extends Stage {

	private final OrthographicCamera camera;

	private final TiledMap map;
	private final TiledMapRenderer renderer;
	private final AssetManager assetManager;
	private final ShapeRenderer srendered;

	private final HashSet<GamePlayer> players;
	private GameObject selected;

	private final Vector3 curr = new Vector3();

	private GameInstance game;
	
	public GameWorld(OrthographicCamera camera) {
		super(new ScreenViewport(camera));
		this.camera = camera;
		this.camera.setToOrtho(false);
		this.camera.update();

		this.assetManager = new AssetManager();
		this.assetManager.setLoader(TiledMap.class, new TmxMapLoader(
				new InternalFileHandleResolver()));
		this.assetManager.load("desert.tmx", TiledMap.class);
		this.assetManager.finishLoading();
		this.map = assetManager.get("desert.tmx");
		this.renderer = new OrthogonalTiledMapRenderer(map);
		this.srendered = new ShapeRenderer();

		this.players = new HashSet<GamePlayer>();
		
		this.setGame(new GameInstance());
		
		this.addPlayer(new GamePlayer(0, this));
		this.addPlayer(new GamePlayer(1, this));
	}

	public boolean addPlayer(GamePlayer player) {
		boolean success = this.getGame().getPlayers().add(player.getPlayer());
		if(success){
			this.players.add(player);
		}
		return success;
	}

	@Override
	public void draw() {
		this.camera.update();
		this.renderer.setView(camera);
		this.renderer.render();

		this.camera.unproject(curr.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		this.srendered.setProjectionMatrix(camera.combined);
		this.srendered.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		this.srendered.begin(ShapeType.Line);
		this.srendered.rect((int)(curr.x / 32) * 32, (int)(curr.y / 32) * 32, 32, 32);
		this.srendered.end();

		super.draw();
	}

	public void resize(int width, int height) {
		this.getViewport().update(width, height);
	}

	@Override
	public void dispose() {
		super.dispose();
		Iterator<GamePlayer> iter = this.players.iterator();
		while (iter.hasNext()) {
			iter.next().dispose();
		}
	}

	public GameInstance getGame() {
		return game;
	}

	public void setGame(GameInstance game) {
		this.game = game;
	}

	public GameObject getSelected() {
		return selected;
	}

	public void mapClick(int x, int y){
		if(this.selected != null){
			this.selected.setGameLocation(x/32, y/32);
			this.selected.setSelected(false);
			this.selected = null;
		}
	}
	
	public void setSelected(GameObject selected) {
		if(this.selected == null){
			this.selected = selected;
			selected.setSelected(true);
		}else{
			if(this.selected == selected){
				this.selected = null;
				selected.setSelected(false);
			}else{
				this.selected.setSelected(false);
				this.selected = selected;
				selected.setSelected(true);
			}	
		}
	}
}
