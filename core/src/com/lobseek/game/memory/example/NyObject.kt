package com.lobseek.game.memory.example

import com.lobseek.game.memory.*

val newNyObject = Pool<NyObject>(32) { NyObject(); }

class NyObject : Poolable {
	internal constructor();
	
	override fun getPool(): AbstractPool<*> {
		return newNyObject;
	}
	
	override fun init(vararg args: Any) {
		
	}
	
	override fun reset() {
		
	}
}