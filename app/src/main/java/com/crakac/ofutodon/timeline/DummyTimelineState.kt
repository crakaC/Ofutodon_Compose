package com.crakac.ofutodon.timeline

import androidx.compose.ui.text.AnnotatedString
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.ui.component.DummyStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlin.coroutines.EmptyCoroutineContext

class
DummyTimelineState(
    scope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
) : StatusTimelineState(scope) {
    private val initialId = 1_000_000L
    override fun refresh() {
        load(FetchType.Next) {
            delay(1000)
            mutableData.postValue(getNextStatuses(initialId))
        }
    }

    override fun fetchNext() {
        load(FetchType.Next, showRefreshing = true) {
            delay(1000)
            val statuses = getNextStatuses(firstStatusId ?: initialId)
            prepend(statuses)
        }
    }

    override fun fetchPrevious() {
        load(FetchType.Previous) {
            delay(1000)
            val statuses = getPreviousStatuses(lastStatusId ?: initialId)
            append(statuses)
        }
    }

    private fun getNextStatuses(
        sinceId: Long = 1_000_000L,
        limit: Int = 20
    ): List<Status> {
        return ((sinceId + limit) downTo (sinceId + 1)).map { DummyStatus.copy(id = it) }
    }

    private fun getPreviousStatuses(
        maxId: Long = 1_000_000L,
        limit: Int = 20
    ): List<Status> {
        return ((maxId - 1) downTo (maxId - limit)).map { DummyStatus.copy(id = it) }
    }

    override fun getName() = AnnotatedString("Debug")
}
