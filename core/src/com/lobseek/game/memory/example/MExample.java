package com.lobseek.game.memory.example;

public class MExample {
	MyObject myObject = MyObject.POOL.lock();
	NyObject nyObject = NyObjectKt.getNewNyObject().lock();
}
