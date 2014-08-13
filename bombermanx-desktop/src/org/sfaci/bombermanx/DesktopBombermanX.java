package org.sfaci.bombermanx;

import static org.sfaci.bombermanx.util.Constants.*;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Clase principal de la versión de escritorio (PC) del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class DesktopBombermanX {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "Bombermanx";

		configuration.width = SCREEN_WIDTH;
		configuration.height = SCREEN_HEIGHT;
		configuration.fullscreen = false;
				
		new LwjglApplication(new Bombermanx(), configuration);
	}
}
