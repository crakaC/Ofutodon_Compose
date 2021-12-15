package com.crakac.ofutodon.timeline

import androidx.compose.ui.text.AnnotatedString
import com.crakac.ofutodon.data.MastodonRepository
import com.crakac.ofutodon.mastodon.params.PageQuery
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.EmptyCoroutineContext

class HomeTimelineState(
    private val repo: MastodonRepository,
    scope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
) : StatusTimelineState(scope) {
    override fun refresh() {
        load {
            mutableData.postValue(repo.getHomeTimeline())
        }
    }

    override fun fetchNext() {
        load(showRefreshing = true) {
            val statuses = repo.getHomeTimeline(PageQuery(sinceId = firstStatusId))
            prepend(statuses)
        }
    }

    override fun fetchPrevious() {
        load {
            val statuses = repo.getHomeTimeline(PageQuery(maxId = lastStatusId))
            append(statuses)
        }
    }

    override fun getName() = AnnotatedString("Home")
}