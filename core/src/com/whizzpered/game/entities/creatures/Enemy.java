package com.whizzpered.game.entities.creatures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.whizzpered.game.entities.Creature;
import com.whizzpered.game.entities.objects.Shell;

import static java.lang.Math.abs;

/**
 * Created by Whizzpered on 18.02.2017.
 */
public class Enemy extends Creature {

    private Shell[] healths = new Shell[16];

    public void add(Shell shell) {
        for (int i = 0; i < healths.length; i++) {
            if (healths[i] == null) {
                healths[i] = shell;
                break;
            }
        }
    }

    public void delete(Shell shell) {
        for (int i = 0; i < healths.length; i++) {
            if (healths[i] == shell) {
                healths[i] = null;
                break;
            }
        }
    }

    public Enemy() {

    }

    @Override
    public void initialize() {
        setX(150);
        setY(150);
        setWidth(50);
        setHeight(50);
        Shell s = new Shell(this);
        getStage().addActor(s);
        add(s);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Hero her = getStage().hero;
        float dist = (float) Math.sqrt(Math.pow(her.getX() - getX(), 2) +
                Math.pow(her.getY() - getY(), 2));
        if (dist > 100) {
            angle = (float) Math.atan2(her.getY() - getY(), her.getX() - getX());
            acceleration = delta;
        } else if (abs(velocity) > delta)
            acceleration = -Float.compare(velocity, 0) * delta;
        else {
            velocity = acceleration = 0;
        }
        for (Shell sh : healths) {
            if (sh != null) {
                sh.velocity = velocity;
                sh.angle = angle;
            }
        }
    }

    @Override
    public void hit(Shell shell) {
        add(shell);
    }

    @Override
    public void draw(Batch b, float parentalpha) {
        b.end();
        ShapeRenderer sp = getStage().sr;
        sp.begin(ShapeRenderer.ShapeType.Filled);
        sp.setProjectionMatrix(getStage().cam.combined);
        sp.setColor(Color.GREEN);
        sp.rect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
        sp.end();
        b.begin();
    }
}
