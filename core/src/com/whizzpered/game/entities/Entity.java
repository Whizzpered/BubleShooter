package com.whizzpered.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.whizzpered.game.stages.Game;
import com.whizzpered.game.terrain.*;

import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by Whizzpered on 17.02.2017.
 */

public class Entity extends Actor {

    public float velocity = 0f, max_velocity = 5f, acceleration = 0f, angle = 0f;
    protected Random r = new Random();
	public enum Dimension {
		X, Y, ALL
	}

    public boolean isCollideCircle(Entity en) {
        float dist = (float) Math.sqrt(Math.pow(getX() - en.getX(), 2) +
                Math.pow(getY() - en.getY(), 2));
        if (dist <= Math.sqrt(Math.pow(en.getWidth() / 2, 2) + Math.pow(getWidth() / 2, 2))) {
            return true;
        }
        return false;
    }

    public boolean isCollideArc(Entity en){
        if(Math.abs(getX()-en.getX()) < getWidth()/2+en.getWidth()/2 &
                Math.abs(getY()-en.getY()) < getHeight()/2+en.getHeight()/2) {
            return true;
        }
        return false;
    }

    @Override
    public Game getStage () {
        return (Game)(super.getStage());
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

	protected void collideWall(Dimension d) {

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
