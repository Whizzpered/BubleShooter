package com.whizzpered.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.whizzpered.game.stages.Game;

public class MyGdxGame extends ApplicationAdapter {

	public static StretchViewport vp = new StretchViewport(480, 800);
	public static MyGdxGame MYGDXGAME;
	public Stage current;
	public Game game;

	@Override
	public void resize(int width, int height) {
		vp.update(width, height);
	}

	public void setCurrent(Stage s) {
		current = s;
		Gdx.input.setInputProcessor(current);
	}

	@Override
	public void create () {
		MYGDXGAME = this;
		game = new Game();
		current = game;
		game.initialize();
	}

	@Override
	public void render() {
		current.act();
		current.draw();
	}
	
	@Override
	public void dispose () {
		game.dispose();
		super.dispose();
	}
}
