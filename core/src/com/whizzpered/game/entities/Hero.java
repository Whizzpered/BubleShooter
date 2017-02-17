package com.whizzpered.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Whizzpered on 17.02.2017.
 */
public class Hero extends Creature {

    public Hero() {
        setX(100);
        setY(100);
    }

    @Override
    public void act(float delta) {
        if (velocity <= max_velocity)
            velocity += acceleration;
        else velocity = max_velocity;
        if(velocity > 0) velocity-=.05f;
        if(velocity < 0) velocity+=.05f;
        setX(getX() + velocity * (float) Math.cos(angle));
        setY(getY() + velocity * (float) Math.sin(angle));
    }

    @Override
    public void draw(Batch b, float parentalpha) {
        b.end();
        ShapeRenderer sp = getStage().sr;
        sp.begin(ShapeRenderer.ShapeType.Filled);
        sp.setProjectionMatrix(getStage().cam.combined);
        //sp.rotate(0,0,0, (float) (angle/ Math.PI * 180));
        sp.setColor(Color.RED);
        sp.rect(getX(), getY(), 50, 50);
        //sp.rotate(0,0,0, (float) -(angle/ Math.PI * 180));
        sp.end();
        b.begin();
    }

}
