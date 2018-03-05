package com.codeandcoke.jbombermanx.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.codeandcoke.jbombermanx.util.Constants;

/**
 * Gestor de la configuración de la partida
 * @author Santiago Faci
 * @version Agosto 2014
 */
public class ConfigurationManager {

    private Preferences prefs;

	public ConfigurationManager() {

        prefs = Gdx.app.getPreferences(Constants.APPNAME);
	}

    public boolean isSoundEnabled() {
        return prefs.getBoolean("sound");
    }


}
