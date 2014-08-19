package org.sfaci.bombermanx.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import org.sfaci.bombermanx.Bombermanx;
import org.sfaci.bombermanx.characters.Bomb;
import org.sfaci.bombermanx.characters.Brick;
import org.sfaci.bombermanx.characters.Enemy;
import org.sfaci.bombermanx.characters.Explosion;
import org.sfaci.bombermanx.characters.Player;
import org.sfaci.bombermanx.screens.GameOverScreen;
import org.sfaci.bombermanx.util.Constants;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Clase que gestiona la lógica de la partida
 * @author Santiago Faci
 * @version Agosto 2014
 */
public class SpriteManager {

	public Player player;
	public Array<Brick> bricks;
	public Array<Bomb> bombs;
	public Array<Enemy> enemies;
    public String hudMessage;
	
	SpriteBatch batch;
    public Bombermanx game;
	LevelManager levelManager;
	
	public SpriteManager(Bombermanx game) {
		
		batch = game.spriteBatch;
        this.game = game;
		
		player = new Player(ResourceManager.assets.get("player/player_idle.png", Texture.class), 0, 0, 3, this);
		bricks = new Array<Brick>();
		bombs = new Array<Bomb>();
		enemies = new Array<Enemy>();
        hudMessage = null;
	}
	
	public void render() {
		
		batch.begin();
        //batch.setProjectionMatrix(game.camera.combined);
			player.render(batch);
			for (Brick brick : bricks)
				brick.render(batch);
			for (Bomb bomb : bombs)
				bomb.render(batch);
			for (Enemy enemy : enemies)
				enemy.render(batch);

            drawHUD();
		batch.end();
	}

    /**
     * Pinta el HUD
     */
    private void drawHUD() {

        if (hudMessage != null) {
            game.font.setScale(2f);
            game.font.draw(batch, hudMessage, 0, Constants.SCREEN_HEIGHT / 2);
            game.font.setScale(0.5f);
        }

        game.font.draw(batch, player.lives + " Vidas", Constants.SCREEN_WIDTH - 40, 10);
    }
	
	public void update(float dt) {
		
		checkBrickCollisions();
		player.update(dt);
		updateBricks(dt);
		updateBombs(dt);
		updateEnemies(dt);
	}
	
	/**
	 * Actualiza la lógica de los enemigos
	 * Comprueba si éstos chocan con ladrillos, bombas o explosiones
	 * También comprueba si éstos deben explotar y morir
	 * También se comprueba si chocan con el jugador
	 * @param dt
	 */
	private void updateEnemies(float dt) {
		
		for (final Enemy enemy : enemies) {	

            /*
             Es necesario comprobar que no está explotando ya por haberse chocado
             Hay que tener en cuenta que la animación dura un rato durante el cual se
             comprueba varias veces la colisión del enemigo con el personaje
             */
            if (player.state != Player.State.EXPLODE)
                if (isCollidable(enemy.position, player.position)) {
                    player.explode();
                    if (player.lives > 1) {
                        // Reinicia el nivel
                        Timer.schedule(new Timer.Task() {
                            public void run() {
                                levelManager.restartCurrentLevel();
                            }
                        }, 3);
                    }
                    else {
                        // El personaje se queda sin vida, se muestra la pantalla de GameOver
                        game.setScreen(new GameOverScreen(game));
                    }
                }
			
			// Si un enemigo choca con un ladrillo o una bomba, cambia de dirección
			for (Brick brick : bricks) {
				if (isCollidable(brick.position, enemy.position))
					enemy.speed = -enemy.speed;
			}
			
			for (Bomb bomb : bombs) {
				// Comprueba si algún enemigo choca con una de las bombas (sin explotar)
				if (isCollidable(bomb.position, enemy.position))
					enemy.speed = -enemy.speed;
			
				// Comprueba si algún enemigo colisiona con la explosión de alguna bomba
				for (Explosion explosion : bomb.explosions) {
					if (isCollidable(explosion.position, enemy.position)) {
						enemy.explode();
					}
				}
			}
			// Si el enemigo ha muerto se elimina de la lista
			if (enemy.isDead()) {
				enemies.removeValue(enemy, true);
			}
			enemy.update(dt);
		}	
	}
	
	/**
	 * Actualiza las bombas del jugador y como afectan las explosiones
	 * de éstas
	 * @param dt
	 */
	private void updateBombs(float dt) {
		for (Bomb bomb : bombs) {
			if (bomb.isDead())
				bombs.removeValue(bomb, true);
			else
				bomb.update(dt);
			
			for (Explosion explosion : bomb.explosions) {
				for (Brick brick : bricks) {
					// Comprueba si alguna explosión ha alcanzado algún ladrillo
					if (isCollidable(brick.position, explosion.position))
						// Sólo se rompen los ladrillos normales
						if (brick.type == Brick.BrickType.BRICK)
							brick.explode();
			
					// Si una explosión alcanza al jugador, éste explota
                    /*
                     Es necesario comprobar que no está explotando ya por haberse chocado
                     Hay que tener en cuenta que la animación dura un rato durante el cual se
                     comprueba varias veces la colisión de la explosión con el personaje
                     */
                    if (player.state != Player.State.EXPLODE)
                        if (isCollidable(player.position, explosion.position)) {
                            player.explode();
                            if (player.lives > 1) {
                                // Reinicia el nivel
                                Timer.schedule(new Timer.Task() {
                                    public void run() {
                                        levelManager.restartCurrentLevel();
                                    }
                                }, 3);
                            }
                            else {
                                // El personaje se queda sin vida, se muestra la pantalla de GameOver
                                game.setScreen(new GameOverScreen(game));
                            }
                        }
				}
			}
		}
	}
	
	/**
	 * Actualiza los ladrillos en pantalla
	 * Si deben o no desaparecer porque han sido destruidos
	 * También comprueba si un ladrillo roto debe generar un powerup
	 * @param dt
	 */
	private void updateBricks(float dt) {
		
		for (Brick brick : bricks)
			if (brick.isDead()) {
				bricks.removeValue(brick, true);
				// Calcula si aparece un powerup en su lugar
				Brick powerup = null;
				int powerupRate = MathUtils.random(0, 200);
				if (powerupRate < 10) {
					powerup = new Brick(ResourceManager.assets.get("powerups/bomb_length.png", Texture.class), brick.position.x, brick.position.y,
						Brick.BrickType.POWERUP_BOMB_LENGTH, 1, 1);
				}
				else if ((powerupRate > 10) && (powerupRate < 20)) {
					powerup = new Brick(ResourceManager.assets.get("powerups/bomb.png", Texture.class), brick.position.x, brick.position.y,
							Brick.BrickType.POWERUP_BOMB, 1, 1);
				}
				else if ((powerupRate > 20) && (powerupRate < 30)) {
					powerup = new Brick(ResourceManager.assets.get("powerups/speed.png", Texture.class), brick.position.x, brick.position.y,
							Brick.BrickType.POWERUP_SPEED, 1, 1);
				}
				else if ((powerupRate > 30) && (powerupRate < 40)) {
					powerup = new Brick(ResourceManager.assets.get("powerups/life.png", Texture.class), brick.position.x, brick.position.y,
						Brick.BrickType.POWERUP_LIFE, 1, 1);
				}
				else
					continue;
				
				bricks.add(powerup);	
			}
			else
				brick.update(dt);
	}
	
	/**
	 * Devuelve si un punto x,y corresponde a algún elemento con el que colisionar
	 * @param brickPosition La posición del ladrillo
	 * @param position La pocisión del personaje
	 * @return Si ladrillo y personaje colisionan
	 */
	private boolean isCollidable(Vector2 brickPosition, Vector2 position) {
				
		float brickX = brickPosition.x;
		float brickY = brickPosition.y;
		float x = position.x;
		float y = position.y;
		
		if ((brickX < (x + Constants.PLAYER_WIDTH)) && ((brickX + Constants.BRICK_WIDTH) > x) 
			&& (brickY < (y + Constants.PLAYER_HEIGHT) && (brickY + Constants.BRICK_HEIGHT) > y))
			
			return true;
		
		return false;
	}
	
	/**
	 * Comprueba las colisiones del jugador con los ladrillos
	 */
	private void checkBrickCollisions() {
	
		for (Brick brick : bricks) {
			
			// Comprueba si el jugador choca con algún ladrillo
			if (isCollidable(brick.position, player.position)) {
				
				switch (brick.type) {
				case POWERUP_BOMB_LENGTH:
					brick.setDead(true);
					player.bombLength++;
					continue;
				case POWERUP_BOMB:
					brick.setDead(true);
					player.bombsLimit++;
					continue;
				case POWERUP_SPEED:
					brick.setDead(true);
					player.speed += 5;
					continue;
				case POWERUP_LIFE:
					brick.setDead(true);
					player.lives++;
					continue;
				case DOOR:
					levelManager.passLevel();
					break;
				default:
					switch (player.state) {
					case LEFT:
						player.position.x = brick.position.x + Constants.BRICK_WIDTH;
						break;
					case RIGHT:
						player.position.x = brick.position.x - Constants.PLAYER_WIDTH;
						break;
					case UP:
						player.position.y = brick.position.y - Constants.PLAYER_HEIGHT;
						break;
					case DOWN:
						player.position.y = brick.position.y + Constants.BRICK_HEIGHT;
						break;
					default:
					}
				}
			}
		}
	}
}
