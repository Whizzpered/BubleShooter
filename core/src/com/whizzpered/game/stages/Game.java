package com.whizzpered.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.whizzpered.game.MyGdxGame;
import com.whizzpered.game.entities.Hero;

/**
 * Created by Whizzpered on 16.02.2017.
 */

public class Game extends Stage {

    public Hero hero;
    private InputAdapter tmp;
    public OrthographicCamera cam;

    //Можно удалить
    public ShapeRenderer sr;


    public Game(){

    }

    public void initialize(){
        initListener();
        cam = new OrthographicCamera();
        cam.setToOrtho(true, MyGdxGame.vp.getWorldWidth(), MyGdxGame.vp.getWorldHeight());
        cam.update();
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        hero = new Hero();
        addActor(hero);
        //cam.translate(hero.getX() - MyGdxGame.vp.getWorldWidth()/2, hero.getY()- MyGdxGame.vp.getWorldHeight()/2);
    }

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
        };
        Gdx.input.setInputProcessor(tmp);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        hero.act(delta);
    }

    @Override
    public void draw(){
        Batch b = getBatch();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        b.begin();
        cam.update();
        b.setProjectionMatrix(cam.combined);
        hero.draw(b,1f);
        b.end();
    }

}
