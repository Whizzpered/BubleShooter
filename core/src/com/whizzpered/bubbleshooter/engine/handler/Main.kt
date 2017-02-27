package com.whizzpered.bubbleshooter.engine.handler

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.whizzpered.bubbleshooter.game.Game
import kotlin.concurrent.thread

object Main : ApplicationAdapter() {
    class Delta {
        private var last = System.currentTimeMillis()

        operator fun invoke(): Float {
            val cur = System.currentTimeMillis()
            val delta = cur - last
            last = cur
            return delta / 1000f
        }
    }

    internal var renderThread: Thread? = null
    internal var paused = false
    internal var dispose = false
    internal var atlas: TextureAtlas? = null
    var batch: SpriteBatch? = null
        internal set
    var img: Texture? = null
        internal set

    fun isRenderThread(): Boolean {
        return Thread.currentThread() == renderThread
    }

    override fun create() {
        batch = SpriteBatch()
        Game.init()
        thread {
            val actDelta = Delta()
            while (!dispose)
                if (!paused) {
                    Game.act(actDelta())
                }
        }
        thread {
            val aiDelta = Delta()
            while (!dispose)
                if (!paused) {
                    Game.ai(aiDelta())
                }
        }
        println(Gdx.files.internal("pack.atlas").file()?.absolutePath)
        if (Gdx.files.internal("pack.atlas").exists())
            println("Exists")
        else
            println("Doesn't exist")

        atlas = TextureAtlas(Gdx.files.internal("pack.atlas"))

    }

    fun createSprite(name: String): Sprite? {
        val a = atlas
        if (a != null) {
            a.regions?.forEach { println(it.name) }
        }
        return atlas?.createSprite(name)
    }

    val renderDelta = Delta()
    override fun render() {
        if (Gdx.graphics.width > Gdx.graphics.height) {
            Game.camera.viewportWidth = Gdx.graphics.width / (Gdx.graphics.height / Game.height)
            Game.camera.viewportHeight = Game.height
        } else {
            Game.camera.viewportHeight = Gdx.graphics.height / (Gdx.graphics.width / Game.height)
            Game.camera.viewportWidth = Game.height
        }
        Game.camera.update()
        batch?.projectionMatrix = Game.camera.projection

        renderThread = Thread.currentThread()
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)

        if (atlas == null)
            Gdx.gl.glClearColor(1f, 1f, 0f, 1f)

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Game.render(renderDelta())
    }

    override fun dispose() {
        dispose = true
        batch?.dispose()
        img?.dispose()
    }


    override fun resize(width: Int, height: Int) {

    }

    override fun pause() {
        paused = true
    }

    override fun resume() {
        paused = false
    }
}
