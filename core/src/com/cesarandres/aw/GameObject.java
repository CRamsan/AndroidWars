package com.cesarandres.aw;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.cesarandres.aw.model.Entity;

public class GameObject extends Actor {
	private Texture texture;
	private float stateTime;
	private Animation leftWalk;
	private Animation rightWalk;
	private boolean isSelected;
	private Entity entity;
	
	public GameObject(int x, int y, final GamePlayer parent) {
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
				parent.getWorld().setSelected(GameObject.this);
				System.out.println("up");
			}
		});
		
		this.setEntity(new Entity(x, y, parent.getPlayer()));
		
		this.setX(x * 32);
		this.setY(y * 32);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {		
		batch.draw(rightWalk.getKeyFrame(this.stateTime, true), 
				this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), 
				this.getWidth(), this.getHeight(), 
				this.getScaleX(), this.getScaleY(), 
				getRotation());
	}

	@Override
	public void act (float delta) {
		stateTime += delta;
	}
	
	public void dispose () {
		texture.dispose();
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public boolean setGameLocation(int x, int y, int parentId){
		if(parentId == this.getEntity().getParent().getID()){
			this.getEntity().setX(x);
			this.getEntity().setY(y);
			this.setX(x * 32);
			this.setY(y * 32);
			return true;
		}
		return false;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if(this.isSelected){
			this.setScale(1.5f);
		}else{
			this.setScale(1f);
		}
	}
}
