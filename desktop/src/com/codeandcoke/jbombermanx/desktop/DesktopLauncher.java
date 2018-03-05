package com.codeandcoke.jbombermanx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.codeandcoke.jbombermanx.Bombermanx;

import static com.codeandcoke.jbombermanx.util.Constants.SCREEN_HEIGHT;
import static com.codeandcoke.jbombermanx.util.Constants.SCREEN_WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bombermanx";

        config.width = SCREEN_WIDTH;
        config.height = SCREEN_HEIGHT;
        config.fullscreen = false;

		new LwjglApplication(new Bombermanx(), config);
	}
}
