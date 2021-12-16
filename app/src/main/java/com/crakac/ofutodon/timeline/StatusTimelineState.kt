package com.crakac.ofutodon.timeline

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crakac.ofutodon.mastodon.entity.Status
import com.google.accompanist.swiperefresh.SwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class StatusTimelineState(parentScope: CoroutineScope) : TimelineState<Status> {
    private val scope = CoroutineScope(parentScope.coroutineContext + Job())
    private val jobs = mutableMapOf<FetchType, Job>()
    protected val mutableData = MutableLiveData<List<Status>>()
    override val data: LiveData<List<Status>>
        get() = mutableData

    override lateinit var swipeRefreshState: SwipeRefreshState

    final override val loadingState = mutableStateOf(false)

    val firstStatusId: Long?
        get() = data.value?.first()?.id

    val lastStatusId: Long?
        get() = data.value?.last()?.id

    override fun update(updated: Status) {
        var changed = false
        val copy by lazy { mutableData.value!!.toMutableList() }
        mutableData.value?.forEachIndexed { index, status ->
            if (status.id == updated.id) {
                copy[index] = updated
                changed = true
            } else if (status.reblog?.id == updated.id) {
                copy[index] = copy[index].copy(reblog = updated)
                changed = true
            }
            if (changed) return@forEachIndexed
        }
        if (changed) {
            mutableData.postValue(copy)
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
        mutableData.postValue(items + (mutableData.value ?: emptyList()))
    }

    protected fun append(items: List<Status>) {
        if (items.isEmpty()) return
        mutableData.postValue((mutableData.value ?: emptyList()) + items)
    }

    fun cancel() {
        jobs.values.forEach { it.cancel() }
    }
}