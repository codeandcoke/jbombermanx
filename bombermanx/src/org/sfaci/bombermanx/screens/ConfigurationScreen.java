package org.sfaci.bombermanx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.sfaci.bombermanx.Bombermanx;

import com.badlogic.gdx.Screen;
import org.sfaci.bombermanx.util.Constants;

/**
 * Pantalla de configuración
 * @author Santiago Faci
 * @version Agosto 2014
 */
public class ConfigurationScreen implements Screen {

	Bombermanx game;
    private Stage stage;
    private Preferences prefs;
	
	public ConfigurationScreen(Bombermanx game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // Pinta el menú
        stage.act();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
        stage.setViewport(width, height);
	}

	@Override
	public void show() {

        loadPreferences();
        loadScreen();
	}

    private void loadScreen() {

        stage = new Stage();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();

        Label title = new Label("JBOMBERMANX\nSETTINGS", game.getSkin());
        title.setFontScale(2.5f);

        final CheckBox checkSound = new CheckBox("SOUND", game.getSkin());
        checkSound.setChecked(prefs.getBoolean("sound"));
        checkSound.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                prefs.putBoolean("sound", checkSound.isChecked());
            }
        });

        TextButton exitButton = new TextButton("MAIN MENU", game.getSkin());
        exitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                prefs.flush();
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Label aboutLabel = new Label("jbombermanx v0.1\n(c) Santiago Faci\nhttp://bitbucket.org/sfaci/jbombermanx", game.getSkin());
        aboutLabel.setFontScale(1f);

        table.row().height(150);
        table.add(title).center().pad(35f);
        table.row().height(20);
        table.add(checkSound).center().pad(5f);
        table.row().height(40);
        table.add(exitButton).center().width(200).pad(5f);
        table.row().height(40);
        table.add(aboutLabel).center().pad(55f);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void loadPreferences() {
        prefs = Gdx.app.getPreferences(Constants.APPNAME);

        // Coloca los valores por defecto (para la primera ejecución)
        if (!prefs.contains("sound"))
            prefs.putBoolean("sound", true);
    }

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
