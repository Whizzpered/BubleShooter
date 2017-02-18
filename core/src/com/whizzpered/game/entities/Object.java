package com.whizzpered.game.entities;

/**
 * Created by Whizzpered on 18.02.2017.
 */
public class Object extends Entity {

    public float lifeTime = 3f;

    public void die(){
        getStage().getActors().removeValue(this, false);
        this.clear();
    }

}
