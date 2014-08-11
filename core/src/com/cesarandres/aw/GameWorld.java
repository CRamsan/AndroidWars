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
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cesarandres.aw.config.ConfigurationFile;
import com.cesarandres.aw.config.Position;
import com.cesarandres.aw.config.StartingPosition;
import com.cesarandres.aw.model.GameInstance;
import com.cesarandres.aw.model.Tile;
import com.cesarandres.aw.util.astart.AStarPathFinder;
import com.cesarandres.aw.util.astart.Path;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class GameWorld extends Stage {

	private final OrthographicCamera camera;

	private final TiledMap gameMap;
	private final com.cesarandres.aw.model.TiledMap map;
	private final TiledMapRenderer renderer;
	private final AssetManager assetManager;
	private final ShapeRenderer srendered;

	private Table<Integer, Integer, GameObject> mapObjects;
	private final HashSet<GamePlayer> players;
	private GameObject selected;

	private AStarPathFinder pathFinder;
	private final Vector3 curr = new Vector3();

	private GameInstance game;

	public GameWorld(OrthographicCamera camera, ConfigurationFile config) {
		super(new ScreenViewport(camera));
		this.camera = camera;
		this.camera.setToOrtho(false);
		this.camera.update();

		this.assetManager = new AssetManager();
		this.assetManager.setLoader(TiledMap.class, new TmxMapLoader(
				new InternalFileHandleResolver()));
		this.assetManager.load(config.getMapFile(), TiledMap.class);
		this.assetManager.finishLoading();
		this.gameMap = assetManager.get("desert.tmx");
		this.renderer = new OrthogonalTiledMapRenderer(this.gameMap);
		this.srendered = new ShapeRenderer();

		this.mapObjects = HashBasedTable.create();
		MapProperties prop = this.gameMap.getProperties();
		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);
		this.map = new com.cesarandres.aw.model.TiledMap(mapWidth, mapHeight);

		TiledMapTileLayer layer = (TiledMapTileLayer) this.gameMap.getLayers()
				.get(0);
		for (int i = 0; i < layer.getHeight(); i++) {
			for (int j = 0; j < layer.getWidth(); j++) {
				if (layer.getCell(j, i).getTile().getProperties()
						.containsKey("isBlocked")) {
					this.map.getTerrain()[j][i] = new Tile(100);
				} else {
					this.map.getTerrain()[j][i] = new Tile(1);
				}

			}
		}

		this.pathFinder = new AStarPathFinder(this.map, false);

		this.players = new HashSet<GamePlayer>();
		this.setGame(new GameInstance());

		for (StartingPosition positions : config.getStartingPositions()) {
			GamePlayer player = new GamePlayer(positions.getPlayer(), this);
			for (Position position : positions.getPositions()) {
				GameObject object = new GameObject(position.getX(),
						position.getY(), player);
				player.addObject(position.getX(), position.getY(), object, this);
			}
			this.addPlayer(player);
		}
	}

	public boolean addPlayer(GamePlayer player) {
		boolean success = this.getGame().getPlayers().add(player.getPlayer());
		if (success) {
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
		this.srendered.rect((int) (curr.x / 32) * 32, (int) (curr.y / 32) * 32,
				32, 32);
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

	public void mapClick(int x, int y) {
		if (this.selected != null) {
			Path path = this.pathFinder.findPath(10, this.selected.getEntity()
					.getX(), this.selected.getEntity().getY(), x / 32, y / 32);
			if (path != null) {
				System.out.println("Path found");
				this.selected.setPath(path);
				this.selected.setSelected(false);
				this.selected = null;
			} else {
				System.out.println("Path NOT found");
			}
		}
	}

	public void setSelected(GameObject selected) {
		if (this.selected == null) {
			this.selected = selected;
			selected.setSelected(true);
		} else {
			if (this.selected == selected) {
				this.selected = null;
				selected.setSelected(false);
			} else {
				this.selected.setSelected(false);
				this.selected = selected;
				selected.setSelected(true);
			}
		}
	}

	public boolean isEmpty(int x, int y) {
		return !this.mapObjects.contains(x, y);
	}

	public void addMapObject(int x, int y, GameObject object) {
		this.mapObjects.put(x, y, object);
		this.map.addMapObject(x, y, object);
		this.addActor(object);
	}

	public GameObject getMapObject(int x, int y, GameObject object) {
		return this.mapObjects.get(x, y);
	}

	public void removeMapObject(int x, int y) {
		this.mapObjects.remove(x, y);
		this.map.removeMapObject(x, y);
	}

	public TiledMap getGameMap() {
		return gameMap;
	}
}
