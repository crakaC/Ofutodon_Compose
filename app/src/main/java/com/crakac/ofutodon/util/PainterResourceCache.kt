package com.crakac.ofutodon.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.crakac.ofutodon.R

class PainterResourceCache {
    private val map = HashMap<Int, Painter>()

    operator fun get(id: Int): Painter? = map[id]

    operator fun set(id: Int, painter: Painter) {
        map[id] = painter
    }

    fun isEmpty() = map.isEmpty()

    fun clear() {
        map.clear()
    }

    @Composable
    fun obtain(id: Int): Painter {
        get(id)?.let { return it }
        val painter = painterResource(id = id)
        set(id, painter)
        return painter
    }
}

@Composable
fun obtainPainterCache(): PainterResourceCache {
    val cache = PainterResourceCache()
    if (cache.isEmpty()) {
        PresetIcons.forEach { id ->
            cache[id] = painterResource(id = id)
        }
    }
    return cache
}

private val PresetIcons = listOf(
    R.drawable.ic_boost,
    R.drawable.ic_direct,
    R.drawable.ic_favourite,
    R.drawable.ic_lock,
    R.drawable.ic_lock_open,
    R.drawable.ic_public,
    R.drawable.ic_more,
    R.drawable.ic_more_vert,
    R.drawable.ic_reply
)

val LocalPainterResource = staticCompositionLocalOf<PainterResourceCache> {
    noLocalProvidedFor("PainterCache")
}

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}