package com.codeandcoke.jbombermanx.screens;

import com.codeandcoke.jbombermanx.Bombermanx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Pantalla de fin de partida. Se muestra cuando el usuario termina una partida
 * Se presenta un menú de game
 * @author Santiago Faci
 * @version Agosto 2014
 */
public class GameOverScreen implements Screen {
	
	final Bombermanx game;
	OrthographicCamera camera;
	
	public GameOverScreen(Bombermanx game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 768);
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0.3f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.spriteBatch.setProjectionMatrix(camera.combined);
		
		// Muestra un menú de inicio
		game.spriteBatch.begin();
		game.font.draw(game.spriteBatch, "Fin del juego!!!!", 100, 150);
		game.font.draw(game.spriteBatch, "Si quieres jugar otra partida pulsa la tecla 'N'", 100, 110);
		game.font.draw(game.spriteBatch, "Pulsa 'ESCAPE' para SALIR", 100, 90);
		game.spriteBatch.end();
		
		/*
		 * Si el usuario toca la pantalla se inicia la partida
		 */
		if (Gdx.input.isKeyPressed(Keys.N)) {
			/*
			 * Aquí habrá que reiniciar algunos aspectos del
			 * game de cara a empezar una nueva partida
			 */			
			game.setScreen(new GameScreen(game));
		}
		/*
		 * El usuario pulsa la tecla ESCAPE, se sale del game
		 */
		else if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			dispose();
			System.exit(0);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
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
		game.dispose();
	}
}
