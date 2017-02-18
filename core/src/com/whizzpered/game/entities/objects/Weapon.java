package com.whizzpered.game.entities.objects;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.whizzpered.game.entities.Creature;

/**
 * Created by Whizzpered on 18.02.2017.
 */
public class Weapon extends com.whizzpered.game.entities.Object {

    Creature owner;
    private float rate = 0f, delay = 0.5f;

    public Weapon(Creature handler) {
        owner = handler;
    }

    @Override
    public void act(float delta) {
        setX(owner.getX() - 25);
        setY(owner.getY() - 25);
        angle = owner.angle;
        if (rate > 0) rate -= delta;
    }

    public void shoot() {
        if (rate <= 0) {
            rate = delay;
            getStage().addActor(new Shell(this));
        }
    }

    @Override
    public void draw(Batch b, float parentalpha) {
        b.end();
        ShapeRenderer sp = getStage().sr;
        sp.begin(ShapeRenderer.ShapeType.Filled);
        sp.setProjectionMatrix(getStage().cam.combined);
        sp.setColor(Color.BLACK);
        sp.rect(getX() - 5, getY() - 10, 10, 20);
        sp.end();
        b.begin();
    }

}
