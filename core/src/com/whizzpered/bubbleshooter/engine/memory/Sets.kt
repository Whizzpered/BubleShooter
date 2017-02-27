package com.whizzpered.bubbleshooter.engine.memory

class UnpureSet<T>(val length: Int) {
    val content: Array<Any?>
    var range: Int = 0

    init {
        content = Array(length) { null }
    }

    fun add(t: T): Boolean {
        var i = 0
        do {
            if (content[i] == t)
                return true
            else if (content[i] == null) {
                content[i] = t
                range = Math.max(i + 1, range)
                return true
            }
        } while (i++ < length)
        return false
    }

    operator fun plusAssign(t: T) {
        add(t)
    }

    operator fun minusAssign(t: T) {
        remove(t)
    }

    fun remove(t: T): Boolean {
        var i = 0
        do {
            if (content[i] == t) {
                content[i] = null
                return true
            }
        } while (i++ < range)
        return false
    }

    fun forEach(eacher: (T) -> Unit) {
        var i = 0
        do {
            val t = content[i]
            if (t != null)
                try {
                    @Suppress("UNCHECKED_CAST")
                    eacher(t as T)
                } catch (e: Exception) {
                }
        } while (i++ < range)
    }
}

class Context<T : Poolable>(val length: Int) {
    private val content = UnpureSet<T>(length)

    fun add(t: T) {
        content.add(t)
    }

    fun remove(t: T) {
        t.unlock()
        content.remove(t)
    }

    operator fun plusAssign(t: T) {
        add(t)
    }

    operator fun minusAssign(t: T) {
        remove(t)
    }

    fun forEach(eacher: (T) -> Unit) {
        content.forEach { eacher(it) }
    }
}