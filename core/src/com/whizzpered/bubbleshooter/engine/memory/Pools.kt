package com.whizzpered.bubbleshooter.engine.memory

interface Poolable {
	fun getPool(): AbstractPool<*>
	fun init()
	fun lock(vararg args: Any)
	fun reset()
	fun unlock() {
		getPool().unlock(this);
	}
}

abstract class AbstractPool<T : Poolable> {
	operator fun invoke(vararg args: Any): T {
		return lock(args)
	}

	abstract fun lock(vararg args: Any): T
	internal abstract fun unlock(obj: Any)
}

open class Pool<T : Poolable> : AbstractPool<T> {
	val fabricator: () -> T
	private val limit: Int
	private val stack = java.util.Stack<T>()

	constructor(limit: Int, fabricator: () -> T) {
		this.limit = limit
		this.fabricator = fabricator
	}

	override fun lock(vararg args: Any): T {
		if (stack.empty()) {
			val v = fabricator()
			v.init()
			v.lock(args)
			return v
		} else {
			val v = stack.pop()
			v.lock(args)
			return v
		}
	}

	override fun unlock(obj: Any) {
		try {
			stack.push(obj as T)
			(obj as T).reset()
		} catch (e: ClassCastException) {
			
		}
	}
}

class UnsafePool<T : Poolable> : Pool<T> {
	constructor(limit: Int, fabricator: () -> T) : super(limit, fabricator)

	public override fun unlock(obj: Any) {
		super.unlock(obj)
	}
}