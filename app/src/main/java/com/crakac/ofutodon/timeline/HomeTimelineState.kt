package com.crakac.ofutodon.timeline

import androidx.compose.ui.text.AnnotatedString
import com.crakac.ofutodon.data.MastodonRepository
import com.crakac.ofutodon.data.params.PageQuery
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.EmptyCoroutineContext

class HomeTimelineState(
    private val repo: MastodonRepository,
    scope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
) : StatusTimelineState(scope) {
    override fun refresh() {
        load(FetchType.Next) {
            prepend(repo.getHomeTimeline())
        }
    }

    override fun fetchNext() {
        load(FetchType.Next, showRefreshing = true) {
            val statuses = repo.getHomeTimeline(PageQuery(sinceId = firstStatusId))
            prepend(statuses)
        }
    }

    override fun fetchPrevious() {
        load(FetchType.Previous) {
            val statuses = repo.getHomeTimeline(PageQuery(maxId = lastStatusId))
            append(statuses)
        }
    }

    override fun getName() = AnnotatedString("Home")
}