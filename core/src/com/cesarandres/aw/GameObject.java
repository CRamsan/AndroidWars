package com.cesarandres.aw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class GameObject extends Actor {
	private TextureRegion region;

	public GameObject() {
		this.region = new TextureRegion(new Texture("badlogic.jpg"));
		this.setBounds(0, 0, this.region.getRegionWidth(), this.region.getRegionHeight());

		this.addListener(new InputListener() {
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        System.out.println("down");
		        return true;
		    }

		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		        System.out.println("up");
		    }
		});
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(),
				getRotation());
	}
	
	@Override
	public Actor hit (float x, float y, boolean touchable) {
	    if (touchable && getTouchable() != Touchable.enabled) return null;
	    return x >= 0 && x < this.getWidth() && y >= 0 && y < this.getHeight() ? this : null;
	}
}
