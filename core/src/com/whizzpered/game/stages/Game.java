package com.whizzpered.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.whizzpered.game.entities.creatures.Hero;

import static java.lang.Math.*;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.*;

/**
 * Created by Whizzpered on 16.02.2017.
 */

public class Game extends Stage {

	public Hero hero;
	public Input input;
	public OrthographicCamera cam;
	public final float CAMERA_MOVEMENT_SHIFT = 35;

	private com.whizzpered.game.terrain.Tile[][] tiles = new com.whizzpered.game.terrain.Tile[64][64];
	private com.whizzpered.game.terrain.Tile defaultTile = new com.whizzpered.game.terrain.Tile(new Color(1, 1, 1, 1));

	public float getWidth() {
		return tiles.length * defaultTile.getSize();
	}
	
	public float getHeight() {
		return tiles[0].length * defaultTile.getSize();
	}

	// Можно удалить
	public ShapeRenderer sr;

	public Game() {
		com.whizzpered.game.terrain.Tile t = new com.whizzpered.game.terrain.Tile(new Color(0, 1, .5f, 1));
		Random r = new Random();
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[x].length; y++)
				if (x % 2 == 0 ^ y % 2 == 0)
					tiles[x][y] = t;
	}

	public com.whizzpered.game.terrain.Tile getTile(int x, int y) {
		while (x < 0)
			x += tiles.length;
		while (x >= tiles.length)
			x -= tiles.length;
		while (y < 0)
			y += tiles[x].length;
		while (y >= tiles[x].length)
			y -= tiles[x].length;
		if (tiles[x][y] != null)
			return tiles[x][y];
		else
			return defaultTile;
	}

	public void initialize() {
		initListener();
		cam = new OrthographicCamera();
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
		hero = new Hero();
		addActor(hero);
		// cam.translate(hero.getX() - MyGdxGame.vp.getWorldWidth()/2,
		// hero.getY()- MyGdxGame.vp.getWorldHeight()/2);
	}

	public void initListener() {
		input = new Input();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (input.getKeyboard().isKeyDown(Key.W, Key.UP))
			hero.acceleration = +delta;
		else if (input.getKeyboard().isKeyDown(Key.S, Key.DOWN))
			hero.acceleration = -delta;
		else if (abs(hero.velocity) > delta)
			hero.acceleration = -Float.compare(hero.velocity, 0) * delta;
		else {
			hero.velocity = hero.acceleration = 0;
		}
		if (input.getKeyboard().isKeyDown(Key.A, Key.LEFT))
			hero.angle -= 5 * delta;
		if (input.getKeyboard().isKeyDown(Key.D, Key.RIGHT))
			hero.angle += 5 * delta;
	}

	@Override
	public void draw() {
		Batch b = getBatch();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		b.begin();
		cam.position.x = hero.getX() + cos(hero.angle) * hero.velocity * CAMERA_MOVEMENT_SHIFT;
		cam.position.y = hero.getY() + sin(hero.angle) * hero.velocity * CAMERA_MOVEMENT_SHIFT;
		cam.update();
		b.setProjectionMatrix(cam.combined);
		/** Этот говнокод временный **/
		sr.begin(ShapeRenderer.ShapeType.Filled);
		System.out.println(
				(int) (cam.position.x / defaultTile.getSize()) + ":" + (int) (cam.position.y / defaultTile.getSize()));
		for (int x = (int) ((-(Gdx.graphics.getWidth() / 2.0) + (cam.position.x)) / defaultTile.getSize())
				- 2; x <= ((Gdx.graphics.getWidth() / 2.0) + (cam.position.x)) / defaultTile.getSize() + 2; x++)
			for (int y = (int) ((-(Gdx.graphics.getHeight() / 2.0) + (cam.position.y)) / defaultTile.getSize())
					- 2; y <= ((Gdx.graphics.getHeight() / 2.0) + (cam.position.y)) / defaultTile.getSize() + 2; y++)
				getTile(x, y).draw(x, y, this);
		/** Именъно такъ **/
		sr.end();
		hero.draw(b, 1f);
		b.end();
	}

}
