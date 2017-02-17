package com.whizzpered.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.badlogic.gdx.math.MathUtils.*;

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
        sp.rect(getX() - 25, getY() - 25, 50, 50);
        sp.setColor(Color.BLUE);
        sp.rect(getX() + cos(angle) * 25 - 2.5f, getY() + sin(angle) * 25 - 2.5f, 5, 5);
        //sp.rotate(0,0,0, (float) -(angle/ Math.PI * 180));
        sp.end();
        b.begin();
    }

}
