package com.whizzpered.game.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.whizzpered.game.stages.Game;

import static java.lang.Math.abs;

/**
 * Created by Whizzpered on 17.02.2017.
 */

public class Entity extends Actor {

    public float velocity = 0f, max_velocity = 5f, acceleration = 0f, angle = 0f;

    public void initialize(){

    }

    @Override
    public Game getStage () {
        return (Game)(super.getStage());
    }

    @Override
    public void act(float delta) {
        if (abs(velocity + acceleration) <= max_velocity)
            velocity += acceleration;
        else velocity = max_velocity * Float.compare(velocity, 0);
        setX(getX() + velocity * (float) Math.cos(angle));
        setY(getY() + velocity * (float) Math.sin(angle));
    }

    public Entity(){

	}

	@Override
	public void setX(float x) {
		final Game game = getStage();
		if (game != null) {
			while(x < game.getWidth())
				x += game.getWidth();
			while(x >= game.getWidth())
				x -= game.getWidth();
		}
		super.setX(x);
	}

	@Override
	public void setY(float y) {
		final Game game = getStage();
		if (game != null) {
			while(y < game.getHeight())
				y += game.getHeight();
			while(y >= game.getHeight())
				y -= game.getHeight();
		}
		super.setY(y);
	}

	public Entity(float x, float y) {
		setX(x);
		setY(y);
	}
}
