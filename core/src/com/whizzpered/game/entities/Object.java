package com.whizzpered.game.entities;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Whizzpered on 18.02.2017.
 */
public class Object extends Entity {

    public Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.WHITE, Color.YELLOW, Color.MAGENTA};

    public float lifeTime = 3f;

    public void die(){
        getStage().getActors().removeValue(this, false);
        this.clear();
    }

}
