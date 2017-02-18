package com.whizzpered.game.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.whizzpered.game.stages.Game;

/**
 * Created by Whizzpered on 17.02.2017.
 */

public class Entity extends Actor {

	@Override
	public Game getStage() {
		return (Game) (super.getStage());
	}

	public Entity() {

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
