package com.lobseek.game.memory.example;

import com.lobseek.game.memory.*;

public class MyObject implements Poolable {
	public static final UnsafePool<MyObject> POOL = new UnsafePool<>(32, () -> {
		return new MyObject();
	});
	
	private MyObject() {
	}
	
	@Override
	public void init(Object... args) {
	}

	@Override
	public AbstractPool<?> getPool() {
		return POOL;
	}

	@Override
	public void reset() {

	}

	@Override
	public void unlock() {
		POOL.unlock(this);
	}
}
