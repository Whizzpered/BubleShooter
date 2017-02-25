package com.whizzpered.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.whizzpered.game.MyGdxGame;
import com.whizzpered.game.entities.Entity;
import com.whizzpered.game.entities.creatures.Enemy;
import com.whizzpered.game.entities.creatures.Hero;
import com.whizzpered.game.terrain.Terrain;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.*;

/**
 * Created by Whizzpered on 16.02.2017.
 */

public class Game extends Stage {

    public Hero hero;
    public Enemy enemy;
    public Joystick joy;
    public static final Random r = new Random();
    public Input input;
    public Terrain terrain = new Terrain(64, 64);
    public OrthographicCamera cam;
    public final float CAMERA_MOVEMENT_SHIFT = 25;
    public ArrayList<Actor> gui = new ArrayList<>();

    public ShapeRenderer sr;

    public float getWidth() {
        return terrain.getWidth() * terrain.getDefaultTile().getSize();
    }

    public float getHeight() {
        return terrain.getHeight() * terrain.getDefaultTile().getSize();
    }

    public Game() {
        com.whizzpered.game.terrain.Tile t = new com.whizzpered.game.terrain.Tile(true, new Color(0, 1, .5f, 1));
    }

    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
        if (actor instanceof Entity)
            ((Entity) actor).initialize();
    }

    public void initialize() {
        initListener();
        initCamera();
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        initCreatures();
        joy = new Joystick(this) {
            @Override
            public void act(float delta) {
                //hero.velocity = this.touchpad.getKnobPercentX() * hero.max_velocity;
            }
        };
        Gdx.input.setInputProcessor(this);
    }

    public void initListener() {
        input = new Input();
    }

    public void initCamera() {
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
    }

    public void initCreatures() {
        hero = new Hero(getWidth() / 2, getHeight() / 2);
        enemy = new Enemy(getWidth() / 2 - 300, getHeight() / 2);
        addActor(enemy);
        addActor(hero);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (Actor a : gui) {
            a.act(delta);
        }
        handleMouse();
        if (input.getKeyboard().isKeyDown(Key.W, Key.UP))
            hero.acceleration = +delta * hero.max_acceleration;
        else if (input.getKeyboard().isKeyDown(Key.S, Key.DOWN)) {
            hero.velocity = -Float.compare(hero.velocity, 0);
            hero.acceleration = -delta * hero.max_acceleration;
        } else if (abs(hero.velocity) > delta)
            hero.acceleration = -Float.compare(hero.velocity, 0) * delta * hero.max_deceleration;
        else {
            hero.velocity = hero.acceleration = 0;
        }
        if (input.getKeyboard().isKeyDown(Key.A, Key.LEFT))
            hero.hor_acceleration = +delta * hero.max_acceleration;
        else if (input.getKeyboard().isKeyDown(Key.D, Key.RIGHT))
            hero.hor_acceleration = -delta * hero.max_acceleration;
        else if (abs(hero.hor_velocity) > delta)
            hero.hor_acceleration = -Float.compare(hero.hor_velocity, 0) * delta * hero.max_deceleration;
        else {
            hero.hor_velocity = hero.hor_acceleration = 0;
        }

        if (input.getKeyboard().isKeyDown(Key.SPACE)) {
            hero.attack();
        }

        if (input.getMouse().isPressed()) {
            hero.attack();
        }

    }

    public void handleMouse() {
        float x = Gdx.input.getX();
        float y = Gdx.input.getY();
        float sx = MyGdxGame.vp.getScreenWidth() / 2;
        float sy = MyGdxGame.vp.getScreenHeight() / 2;
        float angle = (float) Math.atan2(y - sy, x - sx);
        float delta = (float) Math.sqrt(Math.pow(x - sx, 2) + Math.pow(y - sy, 2))
                / (float) Math.sqrt(Math.pow(sx, 2) + Math.pow(sy, 2)) * 5f;
        cam.position.x = hero.getX() + cos(angle) * delta * CAMERA_MOVEMENT_SHIFT;
        cam.position.y = hero.getY() + sin(angle) * delta * CAMERA_MOVEMENT_SHIFT;
        hero.angle = angle;
        cam.update();
    }

    @Override
    public void draw() {
        Batch b = getBatch();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        b.begin();
        b.setProjectionMatrix(cam.combined);
        terrain.draw(this);
        b.end();
        super.draw();
        for (Actor a : gui) {
            a.draw(b, 1f);
        }
    }

}
