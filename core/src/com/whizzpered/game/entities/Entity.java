package com.whizzpered.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.whizzpered.game.stages.Game;
import com.whizzpered.game.terrain.*;

import static java.lang.Math.abs;

/**
 * Created by Whizzpered on 17.02.2017.
 */

public class Entity extends Actor {

	public enum Dimension {
		X, Y, ALL
	}

	public float velocity = 0f, max_velocity = 5f, acceleration = 0f, angle = 0f, size = 0f;

	public void initialize() {

	}

	@Override
	public Game getStage() {
		return (Game) (super.getStage());
	}

	protected void collideWall(Dimension d) {

	}

	@Override
	public void act(float delta) {
		if (abs(velocity + acceleration) <= max_velocity)
			velocity += acceleration;
		else
			velocity = max_velocity * Float.compare(velocity, 0);

		final float nx = getX() + velocity * MathUtils.cos(angle);
		final float ny = getY() + velocity * MathUtils.sin(angle);
		final Terrain t = getStage().terrain;
		if (t.tileAt(nx + MathUtils.cos(angle) * size, ny + MathUtils.sin(angle) * size).getPassable()) {
			setX(nx);
			setY(ny);
		} else {
			if (t.tileAt(nx + MathUtils.cos(angle) * size, getY()).getPassable()) {
				setX(nx);
				collideWall(Dimension.X);
			} else if (t.tileAt(getX(), ny + MathUtils.sin(angle) * size).getPassable()) {
				setY(ny);
				collideWall(Dimension.Y);
			} else
				collideWall(Dimension.ALL);
		}
	}

	public Entity() {

	}

	public Entity(float x, float y) {
		setX(x);
		setY(y);
	}
}
