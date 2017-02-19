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
    Color color = Color.WHITE;

    public Shell(Entity owner) {
        super(owner.getX(), owner.getY());
        this.owner = owner;
    }

    public void setColor() {
        color = colors[r.nextInt(6)];
    }

    public void hit(Shell shell) {
        if (owner != null && shell.owner != null) {
            findGroups(shell);
        }
        if (owner == null && shell.owner != null) {
            owner = shell.owner;
            velocity = 0f;
            ((Enemy) owner).hit(this);
        }
    }

    public void findGroups(Shell shell) {
        if (owner != null && shell.owner != null) {
            Shell[] a = ((Enemy) owner).healths;
            for (Shell s1 : a) {
                if (s1 != null) {
                    for (Shell s2 : a) {
                        if (s2 != null && s1 != s2) {
                            if (s1.isCollideCircle(s2) && s1.color == s2.color) {

                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void initialize() {
        setWidth(20);
        setHeight(20);
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
        if (lifeTime <= 0 && owner == null) die();
        for (Actor a : getStage().getActors()) {
            if (a instanceof Shell && a != this) {
                Shell en = (Shell) a;
                if (isCollideCircle(en)) {
                    hit(en);
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
        sp.begin(ShapeRenderer.ShapeType.Line);
        sp.setProjectionMatrix(getStage().cam.combined);
        sp.setColor(Color.BLACK);
        sp.circle(getX() - 5, getY() - 5, 10);
        sp.end();
        b.begin();
    }
}
