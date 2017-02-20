package com.whizzpered.game.entities.creatures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.whizzpered.game.entities.Creature;
import com.whizzpered.game.entities.objects.Shell;

import static java.lang.Math.abs;

/**
 * Created by Whizzpered on 18.02.2017.
 */
public class Enemy extends Creature {

	public Enemy(float x, float y) {
		super(x, y);
	}

	public Shell[] healths = new Shell[16];
	

	public void add(Shell shell) {
		for (int i = 0; i < healths.length; i++)
			if (healths[i] == null) {
				healths[i] = shell;
				return;
			}
		shell.owner = this;
		if (!shell.match()) {
			float a = (float) (getStage().r.nextDouble() * 2 * Math.PI);
			Enemy e = new Enemy(getX() + MathUtils.cos(a) * 100, getY() + MathUtils.sin(a) * 100);
			e.healths[0] = shell;
			shell.setX(e.getX());
			shell.setY(e.getY());
			shell.owner = e;
			getStage().addActor(e);
		}
	}

	public void delete(Shell shell) {
		for (int i = 0; i < healths.length; i++)
			if (healths[i] == shell) {
				getStage().getActors().removeValue(healths[i], false);
				healths[i] = null;
				return;
			}
	}

	@Override
	public void initialize() {
		Shell s = new Shell(this);
		s.setColor();
		getStage().addActor(s);
		add(s);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		Hero her = getStage().hero;
		float dist = (float) Math.sqrt(Math.pow(her.getX() - getX(), 2) + Math.pow(her.getY() - getY(), 2));
		if (dist > 500) {
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
		/*sp.begin(ShapeRenderer.ShapeType.Filled);
		sp.setProjectionMatrix(getStage().cam.combined);
		sp.setColor(Color.WHITE);
		sp.rect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
		sp.end();*/
		sp.begin(ShapeRenderer.ShapeType.Line);
		sp.setProjectionMatrix(getStage().cam.combined);
		sp.setColor(Color.BLACK);
		sp.rect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
		sp.end();
		b.begin();
	}
}
