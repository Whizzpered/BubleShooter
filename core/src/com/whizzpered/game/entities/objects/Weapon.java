package com.whizzpered.game.entities.objects;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.whizzpered.game.entities.Creature;

import java.util.Random;

/**
 * Created by Whizzpered on 18.02.2017.
 */
public class Weapon extends com.whizzpered.game.entities.Object {

    Creature owner;
    private float rate = 0f, delay = 0.5f;
    Color currentColor;

    public Weapon(Creature handler) {
    	super(handler.getX(), handler.getY());
        owner = handler;
        currentColor = colors[r.nextInt(6)];
    }

    @Override
    public void act(float delta) {
        setX((float) (owner.getX()  + Math.cos(angle) * 25));
        setY((float) (owner.getY()  + Math.sin(angle) * 25));
        //angle = owner.angle;
        if (rate > 0) rate -= delta;
    }

    public void shoot() {
        if (rate <= 0) {
            rate = delay;
            Shell s = new Shell(this);
            s.color = currentColor;
            getStage().addActor(s);
            currentColor = colors[r.nextInt(6)];
        }
    }

    @Override
    public void draw(Batch b, float parentalpha) {
        b.end();
        ShapeRenderer sp = getStage().sr;
        sp.begin(ShapeRenderer.ShapeType.Filled);
        sp.setProjectionMatrix(getStage().cam.combined);
        sp.setColor(currentColor);
        sp.rect(getX() - 5, getY() - 10, 10, 20);
        sp.end();
        b.begin();
    }

}
