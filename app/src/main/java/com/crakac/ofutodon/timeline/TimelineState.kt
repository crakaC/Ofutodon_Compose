package com.crakac.ofutodon.timeline

import androidx.compose.runtime.State
import androidx.compose.ui.text.AnnotatedString
import com.google.accompanist.swiperefresh.SwipeRefreshState

interface TimelineState<T> {
    val data: List<T>
    val loadingState: State<Boolean>
    val swipeRefreshState: SwipeRefreshState
    fun refresh()
    fun fetchNext()
    fun fetchPrevious()
    fun update(updated: T)
    fun getName(): AnnotatedString
}
