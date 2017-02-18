package com.whizzpered.game.entities.creatures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.whizzpered.game.entities.Creature;
import com.whizzpered.game.entities.objects.Weapon;

import static com.badlogic.gdx.math.MathUtils.*;

/**
 * Created by Whizzpered on 17.02.2017.
 */
public class Hero extends Creature {

	@Override
	public void initialize() {
		setX(100);
		setY(100);
		weapon = new Weapon(this);
		getStage().addActor(weapon);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch b, float parentalpha) {
		b.end();
		ShapeRenderer sp = getStage().sr;
		sp.begin(ShapeRenderer.ShapeType.Filled);
		sp.setProjectionMatrix(getStage().cam.combined);
		sp.setColor(Color.RED);
		sp.rect(getX() - 25, getY() - 25, 50, 50);
		sp.setColor(Color.BLUE);
		sp.rect(getX() + cos(angle) * 25 - 2.5f, getY() + sin(angle) * 25 - 2.5f, 5, 5);
		sp.end();
		b.begin();
		if(weapon!=null)weapon.draw(b, parentalpha);
	}

}