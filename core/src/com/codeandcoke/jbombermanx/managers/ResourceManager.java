package com.codeandcoke.jbombermanx.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Clase que gestiona los recursos del juego
 * @author Santiago Faci
 * @version Agosto 2014
 */
public class ResourceManager {

    // Gestiona los recursos
    public static AssetManager assets = new AssetManager();
	
	/**
	 * Carga en memoria todos los recursos del juego (texturas y sonidos)
	 */
	public static void loadAllResources() {

		assets.load("bricks/door.png", Texture.class);
        assets.load("bricks/brick.png", Texture.class);
        assets.load("bricks/broken_brick_animation.png", Texture.class);
		assets.load("bricks/stone.png", Texture.class);
		assets.load("player/player_idle.png", Texture.class);
		assets.load("player/player_right_animation.png", Texture.class);
        assets.load("player/player_left_animation.png", Texture.class);
        assets.load("player/player_up_animation.png", Texture.class);
        assets.load("player/player_down_animation.png", Texture.class);
        assets.load("player/player_explosion_animation.png", Texture.class);
        assets.load("player/bomb_idle.png", Texture.class);
        assets.load("player/bomb_animation.png", Texture.class);

		assets.load("enemy/enemies.pack", TextureAtlas.class);

        assets.load("sounds/bomb.wav", Sound.class);
		
		assets.load("effects/explosion.pack", TextureAtlas.class);

        assets.load("powerups/bomb_length.png", Texture.class);
        assets.load("powerups/bomb.png", Texture.class);
        assets.load("powerups/speed.png", Texture.class);
        assets.load("powerups/life.png", Texture.class);
	}

    /**
     * Se invoca dentro del render() para pausar la carga de los recursos
     * temporalmente y dejar que los menús y el resto del juego carguen
     * @return
     */
    public static boolean update() {
        return assets.update();
    }
}
