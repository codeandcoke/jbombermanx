package com.codeandcoke.jbombermanx.characters;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.codeandcoke.jbombermanx.managers.ResourceManager;

/**
 * Representa las explosiones de las bombas que coloca el jugador
 * @author Santiago Faci
 * @version Agosto 2014
 */
public class Explosion {

	public enum ExplosionType {
		CENTER, UP, DOWN, LEFT, RIGHT, HORIZONTAL, VERTICAL
	}
	
	TextureRegion currentFrame;
	
	Animation<TextureRegion> animation;
	float stateTime;
	public Vector2 position;
	
	public Explosion(float x, float y, ExplosionType explosionType) {

        TextureAtlas atlas = ResourceManager.assets.get("effects/explosion.pack", TextureAtlas.class);
        String strType = null;

		switch (explosionType) {
		case CENTER:
            strType = "explosion_center";
			break;
		case UP:
            strType = "explosion_up";
			break;
		case DOWN:
            strType = "explosion_down";
			break;
		case LEFT:
            strType = "explosion_left";
			break;
		case RIGHT:
            strType = "explosion_right";
			break;
		case HORIZONTAL:
            strType = "explosion_horizontal";
			break;
		case VERTICAL:
            strType = "explosion_vertical";
			break;
		default:
            break;
		}

        animation = new Animation<TextureRegion>(0.2f, atlas.findRegions(strType));
		currentFrame = animation.getKeyFrame(0);
		position = new Vector2(x, y);
	}
	
	public void render(Batch batch) {
		batch.draw(currentFrame, position.x, position.y);
	}
	
	public void update(float dt) {
		
		stateTime += dt;
		currentFrame = animation.getKeyFrame(stateTime, false);
	}
}
