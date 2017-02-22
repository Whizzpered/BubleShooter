package com.whizzpered.game.entities.creatures

class BubbleShell {
	private var ml: MutableList<MutableState> = mutableListOf()
	var states: List<StableState> = listOf()
		private set
	var initialized = false
		private set

	constructor(init: (BubbleShell) -> Unit) {
		init(this)
		val m = ml
		val m2: MutableList<StableState> = mutableListOf()
		m.forEach { m2 += StableState(it) }
		m2.forEach { it.update() }
	}

	internal fun makeState(level: Int, x: Float, y: Float): MutableState {
		val v = MutableState(level, x, y)
		if (!initialized)
			ml.add(v)
		return v
	}

	class MutableState internal constructor(val level: Int, val x: Float, val y: Float) {
		internal val directedPairs: MutableList<MutableState> = mutableListOf()
		operator fun plus(m: MutableState): MutableStateStack {
			val ms = MutableStateStack()
			ms += m
			return ms
		}

		operator fun plusAssign(m: MutableState) {
			directedPairs += m
		}

		operator fun plusAssign(m: MutableStateStack) {
			directedPairs.addAll(m.list)
		}

		internal var child: StableState? = null
	}

	class MutableStateStack {
		internal val list: MutableList<MutableState> = mutableListOf()
		operator fun plus(m: MutableState): MutableStateStack {
			list += m
			return this
		}

		operator fun plusAssign(m: MutableState) {
			list += m
		}
	}

	class StableState {
		val level: Int
		val x: Float
		val y: Float
		val parent: MutableState

		internal constructor(parent: MutableState) {
			this.parent = parent
			parent.child = this
			level = parent.level
			x = parent.x
			y = parent.y
		}

		var pairs: List<StableState> = listOf()
			private set

		internal fun update() {
			val ml: MutableList<StableState> = mutableListOf()
			parent.directedPairs.forEach { val v = it.child; if (v != null) ml.add(v) }
			val l: List<StableState> = ml
			pairs = l
		}
	}
}