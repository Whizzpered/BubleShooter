package com.whizzpered.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.whizzpered.game.MyGdxGame;
import com.whizzpered.game.entities.Entity;
import com.whizzpered.game.entities.creatures.Enemy;
import com.whizzpered.game.entities.creatures.Hero;
import com.whizzpered.game.terrain.Tile;

import static java.lang.Math.*;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.*;

/**
 * Created by Whizzpered on 16.02.2017.
 */

public class Game extends Stage {

    public Hero hero;
    public Enemy enemy;
    public Input input;
    public OrthographicCamera cam;
    public final float CAMERA_MOVEMENT_SHIFT = 25;

    private Tile[][] tiles = new Tile[64][64];
    private Tile defaultTile = new Tile(new Color(1, 1, 1, 1));

    // Можно удалить
    public ShapeRenderer sr;

    public Game() {
        Tile t = new Tile(new Color(0, 1, .5f, 1));
        Random r = new Random();
        for (int x = 0; x < tiles.length; x++)
            for (int y = 0; y < tiles[x].length; y++)
                if (r.nextBoolean())
                    tiles[x][y] = t;
    }

    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
        if (actor instanceof Entity) ((Entity) actor).initialize();
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < tiles.length && y >= 0 && y < tiles[x].length && tiles[x][y] != null)
            return tiles[x][y];
        else
            return defaultTile;
    }

    public void initialize() {
        initListener();
        initCamera();
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        initCreatures();
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
        hero = new Hero();
        enemy = new Enemy();
        addActor(enemy);
        addActor(hero);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        handleMouse();
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
        if (input.getKeyboard().isKeyDown(Key.SPACE)) {
            hero.attack();
        }
        if(input.getKeyboard().tapDown()){
            hero.attack();
        }
    }

    public void handleMouse() {
        float x = Gdx.input.getX();
        float y = Gdx.input.getY();
        float sx = MyGdxGame.vp.getScreenWidth() / 2;
        float sy = MyGdxGame.vp.getScreenHeight() / 2;
        float angle = (float) Math.atan2(y - sy, x - sx);
        float delta = (float) Math.sqrt(Math.pow(x - sx, 2) + Math.pow(y - sy, 2))/
                (float) Math.sqrt(Math.pow(sx, 2) + Math.pow(sy, 2)) * 5f;
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
        /*
        cam.position.x = hero.getX() + cos(angle) * hero.velocity * CAMERA_MOVEMENT_SHIFT;
        cam.position.y = hero.getY() + sin(angle) * hero.velocity * CAMERA_MOVEMENT_SHIFT;
        cam.update();
        */
        b.setProjectionMatrix(cam.combined);
        /** Этот говнокод временный **/
        sr.begin();
        for (int x = (int) ((-(Gdx.graphics.getWidth() / 2.0) + (cam.position.x)) / defaultTile.getSize());
             x <= ((Gdx.graphics.getWidth() / 2.0) + (cam.position.x)) / defaultTile.getSize() + 2;
             x++
                )
            for (int y = (int) ((-(Gdx.graphics.getHeight() / 2.0) + (cam.position.y)) / defaultTile.getSize());
                 y <= ((Gdx.graphics.getHeight() / 2.0) + (cam.position.y)) / defaultTile.getSize() + 2;
                 y++
                    )
                getTile(x, y).draw(x, y, this);
        /** Именъно такъ **/
        sr.end();

        b.end();
        super.draw();
    }

}
