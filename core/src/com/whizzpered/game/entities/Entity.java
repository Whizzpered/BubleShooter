package com.whizzpered.game.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.whizzpered.game.stages.Game;

/**
 * Created by Whizzpered on 17.02.2017.
 */

public class Entity extends Actor{

    @Override
    public Game getStage () {
        return (Game)(super.getStage());
    }

    public Entity(){

    }

    public Entity(float x, float y){
        setX(x);
        setY(y);
    }
}
