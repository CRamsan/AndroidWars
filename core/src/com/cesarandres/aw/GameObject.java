package com.cesarandres.aw;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.cesarandres.aw.util.astart.Path;

public class GameObject extends Actor {
	private Texture texture;
	private float stateTime;
	private Animation leftWalk;
	private Animation rightWalk;
	private boolean isSelected;
	private static GameWorld world;

	public GameObject(int x, int y, GameWorld world) {
		this.texture = new Texture("CWT_INFT.png");
		GameObject.world = world;
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
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				GameObject.world.setSelected(GameObject.this);
			}
		});

		this.setX(x * 32);
		this.setY(y * 32);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(rightWalk.getKeyFrame(this.stateTime, true), this.getX(),
				this.getY(), this.getOriginX(), this.getOriginY(),
				this.getWidth(), this.getHeight(), this.getScaleX(),
				this.getScaleY(), getRotation());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta;
	}

	public void dispose() {
		texture.dispose();
	}

	public void setGameLocation(int x, int y) {
		this.setX(x * 32);
		this.setY(y * 32);
	}

	public int getGameX(){
		return (int) (this.getX() / 32);
	}
	
	public int getGameY(){
		return (int) (this.getY() / 32);
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if (this.isSelected) {
			this.setScale(1.5f);
		} else {
			this.setScale(1f);
		}
	}

	public void setPath(Path path) {
		SequenceAction sequence = new SequenceAction();
		float x = 0, y = 0;
		MoveToAction action;
		for (int i = 0; i < path.getLength(); i++) {
			x = path.getStep(i).getX() * 32;
			y = path.getStep(i).getY() * 32;
			action = new MoveToAction();
			action.setPosition(x, y);
			action.setDuration(0.1f);
			sequence.addAction(action);
		}
		final int initial_x = getGameX();
		final int initial_y = getGameY();
		final int final_x = (int) (x / 32);
		final int final_y = (int) (y / 32);
		sequence.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				GameObject.world.moveMapObject(initial_x, initial_y, final_x,
						final_y, GameObject.this);
				new GameDialog(world);
			}

		}));
		this.addAction(sequence);
	}
}
