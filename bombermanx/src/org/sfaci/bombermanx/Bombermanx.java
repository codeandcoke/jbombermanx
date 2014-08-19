package org.sfaci.bombermanx;

import org.sfaci.bombermanx.managers.ConfigurationManager;
import org.sfaci.bombermanx.screens.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.sfaci.bombermanx.screens.SplashScreen;

/**
 * Clase principal del proyecto principal del juego
 * @author Santiago Faci
 * @version Agosto 2014
 */
public class Bombermanx extends Game {

	//public OrthographicCamera camera;
	public SpriteBatch spriteBatch;
	public BitmapFont font;
	public Skin skin;
    public ConfigurationManager configurationManager;
	
	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
        font.setScale(0.5f);
		
		// Crea la cámara y define la zona de visión del juego (toda la pantalla)
		/*camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		camera.update();*/

        configurationManager = new ConfigurationManager();
		
		//setScreen(new MainMenuScreen(this));
        setScreen(new SplashScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		spriteBatch.dispose();
		font.dispose();
	}
	
	public Skin getSkin() {
		if(skin == null ) {
            skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        }
        return skin;
	}
	
}
