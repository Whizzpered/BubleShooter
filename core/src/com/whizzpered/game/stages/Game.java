package com.whizzpered.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.whizzpered.game.MyGdxGame;
import com.whizzpered.game.entities.Hero;

import static java.lang.Math.*;
import static com.badlogic.gdx.math.MathUtils.*;

/**
 * Created by Whizzpered on 16.02.2017.
 */

public class Game extends Stage {

	public Hero hero;
	public Input input;
	public OrthographicCamera cam;
	public final float CAMERA_MOVEMENT_SHIFT = 35;

	// Можно удалить
	public ShapeRenderer sr;

	public Game() {

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

<<<<<<< HEAD
	public void initListener() {
		input = new Input();
	}
=======
    public void initListener(){
        tmp = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.W) {
                    hero.acceleration = .1f;
                }
                if (keycode == Input.Keys.A) {
                    hero.angle-=0.5f;
                }
                if (keycode == Input.Keys.S) {
                    hero.acceleration = -.1f;
                }
                if (keycode == Input.Keys.D) {
                    hero.angle += 0.5f;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                hero.acceleration = 0f;
                return false;
            }
        };
        Gdx.input.setInputProcessor(tmp);
    }
>>>>>>> master

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
		hero.act(delta);
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
		hero.draw(b, 1f);
		b.end();
	}

}
