package com.whizzpered.game.entities;

import static java.lang.Math.*;

/**
 * Created by Whizzpered on 17.02.2017.
 */

public class Creature extends Entity {

    public float health, damage;
    public float velocity = 0f, max_velocity = 5f, acceleration = 0f, angle = 0f;

    @Override
    public void act(float delta) {
        if (abs(velocity) <= max_velocity)
            velocity += acceleration;
        else velocity = max_velocity;
        setX(getX() + velocity * (float) Math.cos(angle));
        setY(getY() + velocity * (float) Math.sin(angle));
    }

}
