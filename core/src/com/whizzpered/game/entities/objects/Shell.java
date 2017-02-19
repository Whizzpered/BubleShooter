package com.whizzpered.game.entities.objects;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.whizzpered.game.entities.Entity;
import com.whizzpered.game.entities.creatures.Enemy;

/**
 * Created by Whizzpered on 18.02.2017.
 */
public class Shell extends com.whizzpered.game.entities.Object {
	
    Entity owner;
    Color color = Color.BLACK;

    public Shell(Entity owner) {
        this.owner = owner;
    }

    public void hit(Shell shell) {
        if (shell.owner instanceof Enemy) {
            owner = shell.owner;
            velocity = 0f;
            ((Enemy)owner).hit(this);
        }
    }

    @Override
    public void initialize() {
        setX(owner.getX());
        setY(owner.getY());
        setWidth(20);
        angle = owner.angle;
        if (!(owner instanceof Enemy)) {
            max_velocity = 10f;
            velocity = max_velocity;
            if (owner instanceof Weapon) owner = null;
        }
    }
    
    @Override
    protected void collideWall(Dimension d) {
    	if (d == Dimension.X)
    		angle = (float) Math.asin(-Math.sin(angle));
    	else if (d == Dimension.Y)
    		angle = -angle + MathUtils.PI;
    	else 
    		angle += Math.PI;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        lifeTime -= delta;
        if (lifeTime <= 0 & owner == null) die();
        if (owner == null) {
            for (Actor a : getStage().getActors()) {
                if (a instanceof Shell & a != this) {
                    Shell en = (Shell) a;
                    float dist = (float) Math.sqrt(Math.pow(getX() - en.getX(), 2) +
                            Math.pow(getY() - en.getY(), 2));
                    if (dist <= en.getWidth()) {
                        hit(en);

                    }
                }
            }
        }
    }

    @Override
    public void draw(Batch b, float parentalpha) {
        b.end();
        ShapeRenderer sp = getStage().sr;
        sp.begin(ShapeRenderer.ShapeType.Filled);
        sp.setProjectionMatrix(getStage().cam.combined);
        sp.setColor(color);
        sp.circle(getX() - 5, getY() - 5, 10);
        sp.end();
        b.begin();
    }
}
