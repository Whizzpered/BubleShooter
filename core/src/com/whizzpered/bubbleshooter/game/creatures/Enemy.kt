package com.whizzpered.bubbleshooter.game.creatures

import com.whizzpered.bubbleshooter.engine.graphics.Billboard
import com.whizzpered.bubbleshooter.engine.graphics.Model
import com.whizzpered.bubbleshooter.engine.memory.AbstractPool
import com.whizzpered.bubbleshooter.engine.memory.Pool
import com.whizzpered.bubbleshooter.utils.atan2
import com.whizzpered.bubbleshooter.utils.cos
import com.whizzpered.bubbleshooter.utils.dist
import com.whizzpered.bubbleshooter.utils.sin

class Enemy : Creature {

    //POOL\//
    companion object new : Pool<Enemy>(32, { Enemy() })

    override fun getPool(): AbstractPool<*> {
        return new
    }

    internal constructor() : super()
    //\POOL//

    override fun reset() {
    }

    override fun lock(vararg args: Any) {
        if (args.size == 2) {
            if (args[0] is Float) position.x = args[0] as Float
            if (args[1] is Float) position.y = args[1] as Float
        }
    }

    var target = this.position

    val movementHandler = handler { delta ->
        val (tx, ty) = target
        val ta = atan2(ty - position.y, tx - position.x)
        val dist = dist(tx, ty, position.x, position.y)
        if (dist > 2) {
            velocity.x = cos(ta) * MAX_VELOCITY.value
            velocity.y = sin(ta) * MAX_VELOCITY.value
        } else {
            velocity.x = 0f
            velocity.y = 0f
        }
        velocity += acceleration * delta
        position += velocity * delta
        angle = ta
    }

    val model = Model {
        it += Billboard("enemy/left_eye", 0.3f, 0.3f, 0f, .2f, .95f)
        it += Billboard("enemy/right_eye", 0.3f, 0.3f, 0f, -.2f, .95f)
        it += Billboard("enemy/body", 1f, 1f, 0f, 0f, .5f)
    }

    val renderHandler = handler { delta ->
        model.render(position.x, position.y, angle)
    }

    override fun initHandlers() {
        initialActHandlers.add(movementHandler)
        initialRenderHandlers.add(renderHandler)
    }

}