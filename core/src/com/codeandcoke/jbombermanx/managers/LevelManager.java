package com.codeandcoke.jbombermanx.managers;

import com.badlogic.gdx.utils.Timer;
import com.codeandcoke.jbombermanx.characters.Brick;
import com.codeandcoke.jbombermanx.characters.Brick.BrickType;
import com.codeandcoke.jbombermanx.characters.Enemy;
import com.codeandcoke.jbombermanx.characters.Player;
import com.codeandcoke.jbombermanx.screens.GameScreen;
import com.codeandcoke.jbombermanx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que gestiona los niveles del juego
 * @author Santiago Faci
 * @version Agosto 2014
 */
public class LevelManager {

	public int currentLevel;
	public int powerups;
	SpriteManager spriteManager;
	
	public LevelManager(SpriteManager spriteManager) {
		
		this.spriteManager = spriteManager;
		this.spriteManager.levelManager = this;
		
		currentLevel = 1;
		powerups = 0;
	}
	
	/**
	 * Carga el nivel actual leyendo el fichero 'level' + currentLevel + '.txt'
	 */
	public void loadCurrentLevel() {
		
		FileHandle file = Gdx.files.internal("levels/level" + currentLevel + ".txt");
		String levelInfo = file.readString();
		
		int x = 0, y = Constants.SCREEN_HEIGHT - Constants.BRICK_HEIGHT;
		String[] rows = levelInfo.split("\n");
		Brick brick = null;
		for (String row : rows) {
			String[] brickIds = row.split(",");
			for (String brickId : brickIds) {
	
				if (brickId.trim().equals("-")) {
					x += Constants.BRICK_WIDTH;
					continue;
				}
				
				if (brickId.trim().equals("a")) {
					Enemy enemy = new Enemy(x, y, "enemy_blue", Enemy.Direction.RANDOM, spriteManager);
					spriteManager.enemies.add(enemy);
					x += Constants.BRICK_WIDTH;
					continue;
				}
				
				if (brickId.trim().equals("u")) {
					Enemy enemy = new Enemy(x, y, "enemy_ugly", Enemy.Direction.VERTICAL, spriteManager);
					spriteManager.enemies.add(enemy);
					x += Constants.BRICK_WIDTH;
					continue;
				}
				
				if (brickId.trim().equals("b")) {
					Enemy enemy = new Enemy(x, y, "enemy_barrel", Enemy.Direction.VERTICAL, spriteManager);
					spriteManager.enemies.add(enemy);
					x += Constants.BRICK_WIDTH;
					continue;
				}
				
				if (brickId.trim().equals("c")) {
					Enemy enemy = new Enemy(x, y, "enemy_cookie", Enemy.Direction.VERTICAL, spriteManager);
					spriteManager.enemies.add(enemy);
					x += Constants.BRICK_WIDTH;
					continue;
				}

                if (brickId.trim().equals("c")) {
                    Enemy enemy = new Enemy(x, y, "enemy_cookie", Enemy.Direction.HORIZONTAL, spriteManager);
                    spriteManager.enemies.add(enemy);
                    x += Constants.BRICK_WIDTH;
                    continue;
                }
				
				brick = new Brick(getTextureBrick(brickId.trim()), x, y, getBrickType(brickId.trim()), 1, 1);
				spriteManager.bricks.add(brick);
				x += Constants.BRICK_WIDTH;
			}
			
			x = 0;
			y -= Constants.BRICK_HEIGHT;
		}
	}

    /**
     * Reinicia el nivel actual cuando matan al personaje
     */
    public void restartCurrentLevel() {

        spriteManager.player.lives--;
        spriteManager.player.speed = Constants.PLAYER_INITIAL_SPEED;
        spriteManager.player.bombsLimit = 1;
        spriteManager.player.bombLength = 1;
        spriteManager.player.bombStrength = 1;
        resetLevel();
        loadCurrentLevel();
    }
	
	/**
	 * Pasa al siguiente nivel
	 */
	public void passLevel() {

        setLevelClearedMessage();
        currentLevel++;
        resetLevel();
		loadCurrentLevel();
	}

    /**
     * Prepara un mensaje de "fin de nivel" para que se muestre 2 segundos
     */
    private void setLevelClearedMessage() {
        ((GameScreen) spriteManager.game.getScreen()).paused = true;
        spriteManager.hudMessage = "LEVEL " + currentLevel + " CLEARED";
        Timer.schedule(new Timer.Task() {
            public void run() {
                spriteManager.hudMessage = null;
                ((GameScreen) spriteManager.game.getScreen()).paused = false;
            }
        }, 2);
    }
	
	/**
	 * Resetea el nivel actual
	 */
	private void resetLevel() {
		spriteManager.bricks.clear();
		spriteManager.bombs.clear();
		spriteManager.enemies.clear();
		spriteManager.player.position = new Vector2(0, 0);
        spriteManager.player.state = Player.State.IDLE;
	}
	
	/**
	 * Obtiene el tipo de ladrillo según el caracter leído en el fichero de nivel
	 * @param brickId
	 * @return
	 */
	private BrickType getBrickType(String brickId) {
		
		switch (brickId) {
		case "x":
			return BrickType.BRICK;
		case "s":
			return BrickType.STONE;
		case "d":
			return BrickType.DOOR;
		default:
			return null;
		}
	}
	
	/**
	 * Obtiene la textura que corresponde con el tipo de ladrillo dado
	 * @param brickId
	 * @return
	 */
	private Texture getTextureBrick(String brickId) {
		
		switch (brickId) {
		case "x":
			return ResourceManager.assets.get("bricks/brick.png", Texture.class);
		case "s":
			return ResourceManager.assets.get("bricks/stone.png", Texture.class);
		case "d":
			return ResourceManager.assets.get("bricks/door.png", Texture.class);
		default:
			return null;
		}
	}
}
