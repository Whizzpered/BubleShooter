package com.whizzpered.bubbleshooter.game

import com.badlogic.gdx.graphics.OrthographicCamera
import com.whizzpered.bubbleshooter.engine.entities.Entity
import com.whizzpered.bubbleshooter.engine.graphics.MutablePoint
import com.whizzpered.bubbleshooter.game.creatures.Enemy
import com.whizzpered.bubbleshooter.handler.Input
import com.whizzpered.bubbleshooter.engine.memory.Context

object Game {
    val entities = Context<Entity>(256)
    val camera = OrthographicCamera(16f, 12f)
    val height = 6f
    val input = Input()

    fun init() {
        var enemy = Enemy.new()
        enemy.position.set(-2f, 2f)
        enemy.target = MutablePoint(4f, 2f)
        entities += enemy
    }

    fun act(delta: Float) {
        entities.forEach { it.act(delta) }
    }

    fun render(delta: Float) {
        entities.forEach { it.render(delta) }
    }

    fun ai(delta: Float) {
        entities.forEach { it.ai(delta) }
    }
}