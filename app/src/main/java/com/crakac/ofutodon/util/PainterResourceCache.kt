package com.crakac.ofutodon.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.crakac.ofutodon.R

private const val TAG = "PainterCache"
private val PresetIcons = listOf(
    R.drawable.ic_boost,
    R.drawable.ic_direct,
    R.drawable.ic_favourite,
    R.drawable.ic_lock,
    R.drawable.ic_lock_open,
    R.drawable.ic_public,
    R.drawable.ic_more,
    R.drawable.ic_more_vert,
    R.drawable.ic_reply,
)

class PainterResourceCache {
    private val cache = HashMap<Int, Painter>()

    @Composable
    fun presets(): PainterResourceCache {
        PresetIcons.forEach { id ->
            cache[id] = painterResource(id = id)
            Log.i(TAG, "$id is initialized")
        }
        return this
    }

    @Composable
    fun obtain(id: Int): Painter {
        cache[id]?.let { return it }
        val painter = painterResource(id = id)
        cache[id] = painter
        return painter
    }
}

@Composable
fun rememberPainterCache(): PainterResourceCache {
    return remember { PainterResourceCache() }.presets()
}

val LocalPainterResource = staticCompositionLocalOf<PainterResourceCache> {
    error("CompositionLocal PainterResourceCache not present")
}
