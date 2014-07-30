package com.cesarandres.aw;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameWorld extends Stage{

	final OrthographicCamera camera;
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();

	private TiledMap map;
	private TiledMapRenderer renderer;
	private AssetManager assetManager;

	public GameWorld () {
		super(new ScreenViewport(new OrthographicCamera()));
		this.camera = (OrthographicCamera) this.getCamera();
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

		GameObject test = new GameObject();
		this.addActor(test);
	}

	@Override
	public void draw(){
		this.camera.update();
		this.renderer.setView(camera);
		this.renderer.render();
		super.draw();
	}
	
	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		this.camera.unproject(this.curr.set(x, y, 0));
		if (!(this.last.x == -1 && this.last.y == -1 && this.last.z == -1)) {
			this.camera.unproject(this.delta.set(this.last.x, this.last.y, 0));
			this.delta.sub(this.curr);
			this.camera.position.add(this.delta.x, this.delta.y, 0);
		}
		this.last.set(x, y, 0);
		return true;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		this.last.set(-1, -1, -1);
		return true;
	}
	
	@Override
	public boolean scrolled (int ammount) {
		float newZoom = this.camera.zoom + (float)ammount / 10f;
		if(newZoom >= 0.5f && newZoom <= 5f){
			this.camera.zoom = newZoom;
			return true;
		}else{
			return false;
		}
	}
	
	public void resize(int width, int height){
		this.getViewport().update(width, height);
	}
}
