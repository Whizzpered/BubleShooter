package com.whizzpered.bubbleshooter.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.whizzpered.bubbleshooter.engine.handler.Main

object DesktopLauncher {
    @SuppressWarnings
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        LwjglApplication(Main, config)
    }
}
