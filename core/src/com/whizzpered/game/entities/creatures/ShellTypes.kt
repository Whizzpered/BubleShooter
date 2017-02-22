package com.whizzpered.game.entities.creatures

enum class ShellTypes(val shell: BubbleShell) {
	SQUAD(BubbleShell() {
		val v1 = it.makeState(1, 0f, 0f)
		val v2 = it.makeState(2, 1f, 0f)
		val v3 = it.makeState(2, 1f, 1f)
		val v4 = it.makeState(2, 0f, 1f)
		val v5 = it.makeState(2, -1f, 1f)
		val v6 = it.makeState(2, -1f, 0f)
		val v7 = it.makeState(2, -1f, -1f)
		val v8 = it.makeState(2, 0f, -1f)
		val v9 = it.makeState(2, 1f, -1f)
		
		v1 += v2 + v4 + v6 + v8
		v2 += v1 + v3 + v9
		v3 += v2 + v4
		v4 += v1 + v3 + v5
		v5 += v4 + v6
		v6 += v1 + v5 + v7
		v7 += v6 + v8
		v8 += v1 + v7 + v9
		v9 += v2 + v8
	})
}