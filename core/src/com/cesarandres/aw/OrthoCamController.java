/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.cesarandres.aw;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class OrthoCamController extends InputAdapter {
	final OrthographicCamera camera;
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();
	final GameWorld world;

	public OrthoCamController(OrthographicCamera camera, GameWorld world) {
		this.camera = camera;
		this.world = world;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		this.camera.unproject(this.curr.set(x, y, 0));
		if (!(this.last.x == -1 && this.last.y == -1 && this.last.z == -1)) {
			this.camera.unproject(this.delta.set(this.last.x, this.last.y, 0));
			this.delta.sub(this.curr);
			this.camera.position.add(this.delta.x, this.delta.y, 0);
		}
		this.last.set(x, y, 0);
		return false;
	}
	
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		this.camera.unproject(this.curr.set(x, y, 0));
		this.world.mapClick((int)this.curr.x, (int)this.curr.y);
		this.last.set(-1, -1, -1);
		return false;
	}

	@Override
	public boolean scrolled(int ammount) {
		float newZoom = this.camera.zoom + (float) ammount / 50f;
		if (newZoom >= 0f && newZoom <= 5f) {
			this.camera.zoom = newZoom;
			return true;
		} else {
			return false;
		}
	}
}
