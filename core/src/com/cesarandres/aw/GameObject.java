package com.cesarandres.aw;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class GameObject extends Actor {
	private Texture texture;
	private float stateTime;
	private Animation leftWalk;
	private Animation rightWalk;

	public GameObject() {
		this.texture = new Texture("CWT_INFT.png");
		TextureRegion[] leftWalkFrames = TextureRegion.split(this.texture, 32,
				32)[0];
		TextureRegion[] rightWalkFrames = new TextureRegion[leftWalkFrames.length];
		this.setBounds(0, 0, 32, 32);

		for (int i = 0; i < rightWalkFrames.length; i++) {
			TextureRegion frame = new TextureRegion(leftWalkFrames[i]);
			frame.flip(true, false);
			rightWalkFrames[i] = frame;
		}
		leftWalk = new Animation(0.25f, leftWalkFrames);
		rightWalk = new Animation(0.25f, rightWalkFrames);

		this.stateTime = 0f;

		this.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("up");
			}
		});

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {		
		batch.draw(rightWalk.getKeyFrame(this.stateTime, true), this.getX(), this.getY());
	}

	@Override
	public void act (float delta) {
		stateTime += delta;
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && getTouchable() != Touchable.enabled)
			return null;
		return x >= 0 && x < this.getWidth() && y >= 0 && y < this.getHeight() ? this
				: null;
	}
	
	public void dispose () {
		texture.dispose();
	}
}
