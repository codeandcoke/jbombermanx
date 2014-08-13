package org.sfaci.bombermanx.characters;

import com.badlogic.gdx.math.MathUtils;
import org.sfaci.bombermanx.managers.ResourceManager;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import org.sfaci.bombermanx.managers.SpriteManager;
import org.sfaci.bombermanx.util.Constants;

/**
 * Clase que representa a los enemigos de diferentes tipos
 * que pueden aparecer
 * @author Santiago Faci
 * @version 1.0
 */
public class Enemy extends Character {

	public enum Direction {
		VERTICAL, HORIZONTAL, RANDOM
	}
	Direction direction;
	boolean exploding;
	boolean dead;
	String name;

	Animation animation;
	float stateTime;
	public float speed = 30f;
    float lastJump = 0;

    SpriteManager spriteManager;
	
	public Enemy(float x, float y, String name, Direction direction, SpriteManager spriteManager) {
		super(x, y);
		
		this.name = name;
		animation = new Animation(0.15f, ResourceManager.getAtlas("enemies").findRegions(name));
		
		currentFrame = animation.getKeyFrame(0);
		rect.width = currentFrame.getRegionWidth();
		rect.height = currentFrame.getRegionHeight();
		this.direction = direction;

        this.spriteManager = spriteManager;
	}

	public void move(Vector2 movement) {
		
		movement.scl(speed);
		position.add(movement);
	}
	
	public void explode() {
		exploding = true;
		speed = 0f;
		
		// En 1.5 segundos desaparecerá de la pantalla
		Timer.schedule(new Task() {
			public void run() {
				die();
			}
		}, 1.5f);
	}
	
	public void die() {
		dead = true;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	@Override
	public void update(float dt) {
		
		super.update(dt);
		
		stateTime += dt;
		if (!exploding)
			currentFrame = animation.getKeyFrame(stateTime, true);
		else
			currentFrame = ResourceManager.getAtlas("enemies").findRegion(name + "_dead");
		
		switch (direction) {
		case VERTICAL:
			move(new Vector2(0, -dt));
			break;
		case HORIZONTAL:
			move(new Vector2(dt, 0));
			break;
        case RANDOM:
            // Cambia de posición aleatoriamente a algún hueco libre
            if (stateTime - lastJump > 5) {
                int x = MathUtils.random(0, Constants.MAP_WIDTH - 1);
                int y = MathUtils.random(0, Constants.MAP_HEIGHT - 1);
                boolean isABrick = false;

                for (Brick brick : spriteManager.bricks) {
                    if ((brick.position.x == x) && (brick.position.y == y))
                        isABrick = true;
                }

                if (!isABrick) {
                    position.x = x * Constants.BRICK_WIDTH;
                    position.y = y * Constants.BRICK_HEIGHT;
                    lastJump = stateTime;
                }
            }
        }
	}
}
