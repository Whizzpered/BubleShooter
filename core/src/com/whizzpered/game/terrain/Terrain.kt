package com.whizzpered.game.terrain

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.Random;
import com.whizzpered.game.MyGdxGame;
import com.whizzpered.game.entities.Entity;
import com.whizzpered.game.entities.creatures.Enemy;
import com.whizzpered.game.entities.creatures.Hero;
import com.whizzpered.game.terrain.Tile;
import com.whizzpered.game.stages.*;

class Terrain(val width: Int, val height: Int) {

	private val tiles: Array<Array<Tile>> = Array(width) { Array(height) { defaultTile } }
	val defaultTile = Tile(passable = true, color = Color(1f, 1f, 1f, 1f))
	val wallTile = Tile(passable = false, color = Color(.5f, .5f, .5f, 1f))

	init {
		val r = Random();
		var f = Array(width) { Array(height) { r.nextInt(500) } }
		var t = Array(width) { Array(height) { r.nextInt(500) } }
		var cx = width / 2;
		var cy = height / 2;
		var dir = 0
		for (i in 0..500) {
			if (r.nextInt(5) == 0)
				dir = r.nextInt(4)
			when (dir) {
				0 -> cx++
				1 -> cx--
				2 -> cy++
				3 -> cy--
			}
			cx = correct(cx, width); cy = correct(cy, height)
			f[cx][cy] = 500 + r.nextInt(1000);
		}

		for (x in 0..width - 1)
			for (i in 0..0) {
				f[x][i] = -r.nextInt(500)
				f[x][height - 1 - i] = -r.nextInt(500)
			}

		for (y in 0..height - 1)
			for (i in 0..0) {
				f[i][y] = -r.nextInt(500)
				f[width - 1 - i][y] = -r.nextInt(500)
			}

		for (i in 0..3) {
			for (x in 0..width - 1)
				for (y in 0..height - 1)
					if (r.nextInt(5) == 0)
						f[x][y] = r.nextInt(500);
			for (x in 0..width - 1)
				for (y in 0..height - 1) {
					var s = 0;
					for (dx in -1..1)
						for (dy in -1..1)
							s += f[correct(x + dx, width)][correct(y + dy, height)]
					t[x][y] = s / 9
				}
			val v = f
			f = t
			t = v
		}

		for (y in 0..height - 1) {
			for (x in 0..width - 1)
				if (f[x][y] < 250) {
					tiles[x][y] = wallTile
					print('@')
				} else {
					tiles[x][y] = defaultTile
					print(' ')
				}
			println();
		}


	}

	private fun correct(x: Int, w: Int): Int {
		var cx = x;
		while (cx < 0)
			cx += w;
		while (cx >= w)
			cx -= w;
		return cx;
	}

	operator fun get(x: Int, y: Int): Tile {
		return tiles[correct(x, width)][correct(y, height)];
	}

	operator fun set(x: Int, y: Int, v: Tile) {
		tiles[correct(x, width)][correct(y, height)] = v;
	}

	fun draw(game: Game) {


		game.sr.begin(ShapeRenderer.ShapeType.Filled);
		/*
		for (x in 0..width-1)
			for (y in 0..height-1)
				tiles[x][y].draw(x, y, game);
			*/

		for (x in ((-Gdx.graphics.width / 2.0 + game.cam.position.x) / defaultTile.size - 3).toInt()..
				((Gdx.graphics.width / 2.0 + game.cam.position.x) / defaultTile.size + 3).toInt())

			for (y in ((-Gdx.graphics.height / 2.0 + game.cam.position.y) / defaultTile.size - 3).toInt()..
					((Gdx.graphics.height / 2.0 + game.cam.position.y) / defaultTile.size + 3).toInt())

				tiles[correct(x, width)][correct(y, height)].draw(x, y, game)


		/*
        for (x in (-(Gdx.graphics.width / 2.0) + (game.cam.position.x)) / defaultTile.size).toInt() ..
        (((Gdx.graphics.width / 2.0) + (game.cam.position.x)).toInt()
        / defaultTile.size + 2).toInt()
        
        for (x in ((-(Gdx.graphics.getWidth() / 2.0) + (game.cam.position.x)).toInt()
                / defaultTile.size)..((Gdx.graphics.getWidth() / 2.0) + (cam.position.x)).toInt()
        / defaultTile.size + 2)
        for (int y = (int)((-(Gdx.graphics.getHeight() / 2.0) + (cam.position.y))
                / defaultTile.getSize()); y <= ((Gdx.graphics.getHeight() / 2.0) + (cam.position.y))
        / defaultTile.getSize() + 2; y++)
        getTile(x, y).draw(x, y, this);
         */
		/** Именъно такъ **/
		game.sr.end();
	}

	fun tileAt(x: Float, y: Float): Tile {
		val nx = ((x + defaultTile.size / 2) / defaultTile.size).toInt()
		val ny = ((y + defaultTile.size / 2) / defaultTile.size).toInt()
		return tiles[correct(nx, width)][correct(ny, height)];
	}
}