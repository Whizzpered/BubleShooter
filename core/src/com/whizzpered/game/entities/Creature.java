package com.whizzpered.game.entities;

import com.badlogic.gdx.Gdx;
import com.whizzpered.game.entities.objects.Weapon;

import static java.lang.Math.*;

/**
 * Created by Whizzpered on 17.02.2017.
 */

public class Creature extends Entity {

    public float health, damage;
    protected Weapon weapon;

    public boolean hasWeapon() {
        return weapon != null;
    }

    public void hit(){
        Gdx.app.log("Enemy:","OUGH");
    }

    public void attack() {
        if (this.hasWeapon()) weapon.shoot();
    }

}
