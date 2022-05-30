package com.crakac.ofutodon.timeline

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.crakac.ofutodon.data.entity.Status
import com.google.accompanist.swiperefresh.SwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class StatusTimelineState(parentScope: CoroutineScope) : TimelineState<Status> {
    private val scope = CoroutineScope(parentScope.coroutineContext + Job())
    private val jobs = mutableMapOf<FetchType, Job>()
    private val mutableData = mutableStateListOf<Status>()
    override val data: List<Status>
        get() = mutableData

    override val swipeRefreshState: SwipeRefreshState = SwipeRefreshState(false)
    final override val loadingState = mutableStateOf(false)

    val firstStatusId: Long?
        get() = data.firstOrNull()?.id

    val lastStatusId: Long?
        get() = data.lastOrNull()?.id

    override fun update(updated: Status) {
        mutableData.forEachIndexed { index, status ->
            if (status.id == updated.id) {
                mutableData[index] = updated
                return
            } else if (status.reblog?.id == updated.id) {
                mutableData[index] = status.copy(reblog = updated)
                return
            }
        }
    }

    /**
     * Only one FetchTYpe can be launched at a time.
     * If same FetchType job is already launched and it is active,
     * fetching request will be ignored.
     */
    fun load(
        fetchType: FetchType,
        showRefreshing: Boolean = false,
        block: suspend () -> Unit
    ) {
        if (jobs[fetchType]?.isActive == true) return
        if (showRefreshing) {
            swipeRefreshState.isRefreshing = true
        }
        loadingState.value = true
        jobs[fetchType] = scope.launch {
            try {
                block()
            } finally {
                if (showRefreshing) {
                    swipeRefreshState.isRefreshing = false
                }
                loadingState.value = jobs.any { it.key != fetchType && it.value.isActive }
            }
        }
    }

    protected fun prepend(items: List<Status>) {
        if (items.isEmpty()) return
        mutableData.addAll(0, items)
    }

    protected fun append(items: List<Status>) {
        if (items.isEmpty()) return
        mutableData.addAll(items)
    }

    fun cancel() {
        jobs.values.forEach { it.cancel() }
    }
}