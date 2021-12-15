package com.crakac.ofutodon.timeline

import androidx.compose.runtime.State
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.LiveData
import com.google.accompanist.swiperefresh.SwipeRefreshState

interface TimelineState<T> {
    val data: LiveData<List<T>>
    val loadingState: State<Boolean>
    var swipeRefreshState: SwipeRefreshState
    fun refresh()
    fun fetchNext()
    fun fetchPrevious()
    fun update(updated: T)
    fun getName(): AnnotatedString
}