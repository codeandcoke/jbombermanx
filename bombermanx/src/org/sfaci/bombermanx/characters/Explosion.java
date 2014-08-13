package org.sfaci.bombermanx.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import org.sfaci.bombermanx.managers.ResourceManager;

public class Explosion {

	public enum ExplosionType {
		CENTER, UP, DOWN, LEFT, RIGHT, HORIZONTAL, VERTICAL
	}
	
	TextureRegion currentFrame;
	
	Animation animation;
	float stateTime;
	public Vector2 position;
	
	public Explosion(float x, float y, ExplosionType explosionType) {

        TextureAtlas atlas = ResourceManager.getAtlas("explosion");
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

        animation = new Animation(0.2f, atlas.findRegions(strType));
		currentFrame = animation.getKeyFrame(0);
		position = new Vector2(x, y);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(currentFrame, position.x, position.y);
	}
	
	public void update(float dt) {
		
		stateTime += dt;
		currentFrame = animation.getKeyFrame(stateTime, false);
	}
}
