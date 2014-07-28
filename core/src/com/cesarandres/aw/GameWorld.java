package com.cesarandres.aw;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameWorld extends Stage{
	final OrthographicCamera camera;
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();

	public GameWorld (Viewport viewport, OrthographicCamera camera) {
		super(viewport);
		this.camera = camera;
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
}
