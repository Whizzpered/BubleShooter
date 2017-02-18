package com.whizzpered.game.entities.objects;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.whizzpered.game.entities.Entity;

import static java.lang.Math.abs;

/**
 * Created by Whizzpered on 18.02.2017.
 */
public class Shell extends com.whizzpered.game.entities.Object {

    Entity owner;

    public Shell(Entity owner){
        this.owner = owner;
    }

    @Override
    public void initialize(){
        max_velocity = 10f;
        setX(owner.getX());
        setY(owner.getY());
        angle = owner.angle;
        velocity = max_velocity;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        lifeTime -= delta;
        if (lifeTime <= 0) {
            getStage().getActors().removeValue(this, false);
            this.clear();
        }
    }

    @Override
    public void draw(Batch b, float parentalpha) {
        b.end();
        ShapeRenderer sp = getStage().sr;
        sp.begin(ShapeRenderer.ShapeType.Filled);
        sp.setProjectionMatrix(getStage().cam.combined);
        sp.setColor(Color.BLACK);
        sp.rect(getX() - 2, getY() - 2, 5, 5);
        sp.end();
        b.begin();
    }
}
