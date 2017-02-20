package com.whizzpered.game.entities;

import com.badlogic.gdx.Gdx;
import com.whizzpered.game.entities.objects.Shell;
import com.whizzpered.game.entities.objects.Weapon;

/**
 * Created by Whizzpered on 17.02.2017.
 */

public class Creature extends Entity {

    public Weapon weapon;

    public Creature(float x, float y) {
        super(x, y);
    }

    public boolean hasWeapon() {
        return weapon != null;
    }

    public void hit(Shell shell){
        Gdx.app.log("Enemy:","OUGH");
    }

    public void attack() {
        if (this.hasWeapon()) weapon.shoot();
    }

}
