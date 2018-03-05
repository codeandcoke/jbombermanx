package com.codeandcoke.jbombermanx.screens;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.codeandcoke.jbombermanx.Bombermanx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Pantalla de inicio
 * Se presenta el menú de game
 * @author Santiago Faci
 * @version Agosto 2014
 */
public class MainMenuScreen implements Screen {
	
	final Bombermanx game;
	private Stage stage;
	
	public MainMenuScreen(Bombermanx game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		loadScreen();
	}

	@Override
	public void render(float dt) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Pinta el menú
		stage.act();
		stage.draw();
	}
	
	private void loadScreen() {
		// Grafo de escena que contiene el menú
		stage = new Stage();
					
		// Crea una tabla, donde añadiremos los elementos de menú
		Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();

        // Etiqueta de texto
        Label label = new Label("JBOMBERMANX", game.getSkin());
        label.setFontScale(2.5f);

        // Botones de menú
        TextButton playButton = new TextButton("NEW GAME", game.getSkin());
        playButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new GameScreen(game));
            }
        });

        TextButton settingsButton = new TextButton("SETTINGS", game.getSkin());
        settingsButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new ConfigurationScreen(game));
            }
        });

        TextButton quitButton = new TextButton("QUIT", game.getSkin());
        quitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                System.exit(0);
            }
        });

        Label aboutLabel = new Label("JBombermanx v0.1\n(c) Santiago Faci\nhttp://bitbucket.org/sfaci/bombermanx", game.getSkin());
        aboutLabel.setFontScale(1f);

        table.row().height(100);
        table.add(label).center().pad(35f);
        table.row().height(40);
        table.add(playButton).center().width(200).pad(5f);
        table.row().height(40);
        table.add(settingsButton).center().width(200).pad(5f);
        table.row().height(40);
        table.add(quitButton).center().width(200).pad(5f);
        table.row().height(40);
        table.add(aboutLabel).center().pad(55f);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
        //stage.setViewport(width, height);
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
