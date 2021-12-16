@file:Suppress("NOTHING_TO_INLINE")

package com.crakac.ofutodon.ui

// Code from https://github.com/chrisbanes/tivi/blob/main/common-ui-compose/src/main/java/app/tivi/common/compose/Debug.kt

/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import com.crakac.ofutodon.BuildConfig

class Ref(var value: Int)

const val EnableDebugCompositionLogs = true

/**
 * An effect which logs the number compositions at the invoked point of the slot table.
 * Thanks to [objcode](https://github.com/objcode) for this code.
 *
 * This is an inline function to act as like a C-style #include to the host composable function.
 * That way we track it's compositions, not this function's compositions.
 *
 * @param tag Log tag used for [Log.d]
 */
@Composable
inline fun LogCompositions(tag: String) {
    if (EnableDebugCompositionLogs && BuildConfig.DEBUG) {
        val ref = remember { Ref(0) }
        SideEffect { ref.value++ }
        Log.d(tag, "Compositions: ${ref.value}")
    }
}


@Composable
inline fun LogOnDispose(tag: String) {
    if (EnableDebugCompositionLogs && BuildConfig.DEBUG) {
        DisposableEffect(Unit) {
            onDispose {
                Log.d(tag, "onDispose()")
            }
        }
    }
}
