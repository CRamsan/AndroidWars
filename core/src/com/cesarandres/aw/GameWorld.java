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
import com.cesarandres.aw.util.astart.AStarPathFinder;
import com.cesarandres.aw.util.astart.Path;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class GameWorld extends Stage {

	private final OrthographicCamera camera;

	private final TiledMap gameMap;
	private final TiledMapRenderer renderer;
	private final AssetManager assetManager;
	private final ShapeRenderer srendered;

	private Table<Integer, Integer, GameObject> mapObjects;
	private final HashSet<GamePlayer> players;
	private GameObject selected;
	private int[][] terrain;
	private AStarPathFinder pathFinder;
	
	private final Vector3 curr = new Vector3();
	private int mapWidth;
	private int mapHeight;
	
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
		this.mapWidth = prop.get("width", Integer.class);
		this.mapHeight = prop.get("height", Integer.class);

		TiledMapTileLayer layer = (TiledMapTileLayer) this.gameMap.getLayers()
				.get(0);
		this.terrain = new int[mapWidth][mapHeight];
		for (int i = 0; i < layer.getHeight(); i++) {
			for (int j = 0; j < layer.getWidth(); j++) {
				if (layer.getCell(j, i).getTile().getProperties()
						.containsKey("isBlocked")) {
					this.terrain[j][i] = 100;
				} else {
					this.terrain[j][i] = 1;
				}

			}
		}

		this.pathFinder = new AStarPathFinder(this, false);
		this.players = new HashSet<GamePlayer>();

		int counter = 0;
		for (StartingPosition positions : config.getStartingPositions()) {
			GamePlayer player = new GamePlayer(positions.getPlayer(), counter, this);
			for (Position position : positions.getPositions()) {
				this.addPlayer(player);
				GameObject object = new GameObject(position.getX(),
						position.getY(), this);
				this.addMapObject(position.getX(), position.getY(), player.getID(),object);
			}
			counter++;
		}
	}

	public boolean addPlayer(GamePlayer player) {
		return this.players.add(player);
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

	public GameObject getSelected() {
		return selected;
	}

	public void mapClick(int x, int y) {
		if (this.selected != null) {
			Path path = this.pathFinder.findPath(10, this.selected.getGameX(), this.selected.getGameY(), x / 32, y / 32);
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

	public void addMapObject(int x, int y, int player, GameObject object) {
		this.mapObjects.put(x, y, object);
		this.addActor(object);
	}

	public GameObject getMapObject(int x, int y, GameObject object) {
		return this.mapObjects.get(x, y);
	}

	public void removeMapObject(int x, int y) {
		GameObject object = this.mapObjects.remove(x, y);
		object.remove();
	}

	public void moveMapObject(int ox, int oy, int dx, int dy, GameObject object) {
		this.mapObjects.remove(ox, oy);
		this.mapObjects.put(dx, dy, object);
		object.setGameLocation(dx, dy);
	}
	
	public int getMapWidth() {
		return this.mapWidth;
	}
	
	public int getMapHeight() {
		return this.mapHeight;
	}
	
	public int getTerrain(int x, int y){
		return this.terrain[x][y];
	}
}
