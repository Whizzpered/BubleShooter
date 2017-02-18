package com.whizzpered.game.stages

import com.badlogic.gdx.graphics.Color;

class Tile(val color: Color) {
	val size = 64f;
	
	fun draw(x: Int, y: Int, game: Game) {
		var sr = game.sr;
		sr.color = color;
		sr.rect(x.toFloat() * size - size / 2f + 1, y.toFloat() * size - size / 2f + 1, size - 2, size - 2)
	}
}