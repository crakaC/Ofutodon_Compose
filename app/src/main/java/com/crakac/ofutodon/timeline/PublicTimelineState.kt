package com.crakac.ofutodon.timeline

import androidx.compose.ui.text.AnnotatedString
import com.crakac.ofutodon.data.MastodonRepository
import com.crakac.ofutodon.data.params.PageQuery
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.EmptyCoroutineContext

class PublicTimelineState(
    private val repo: MastodonRepository,
    private val isLocalOnly: Boolean = true,
    scope: CoroutineScope = CoroutineScope(EmptyCoroutineContext),
) : StatusTimelineState(scope) {
    override fun refresh() {
        load(FetchType.Next) {
            prepend(repo.getPublicTimeline(localOnly = isLocalOnly))
        }
    }

    override fun fetchNext() {
        load(FetchType.Next, showRefreshing = true) {
            val statuses =
                repo.getPublicTimeline(
                    localOnly = isLocalOnly,
                    PageQuery(sinceId = firstStatusId),
                )
            prepend(statuses)
        }
    }

    override fun fetchPrevious() {
        load(FetchType.Previous) {
            val statuses =
                repo.getPublicTimeline(
                    localOnly = isLocalOnly,
                    PageQuery(maxId = lastStatusId),
                )
            append(statuses)
        }
    }

    override fun getName() = AnnotatedString(if (isLocalOnly) "Local" else "Public")
}
