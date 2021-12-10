package com.crakac.ofutodon.data

import com.crakac.ofutodon.mastodon.Mastodon
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.mastodon.params.StatusBody
import javax.inject.Inject

class MastodonRepository @Inject constructor(
    private val mastodon: Mastodon
) {
    suspend fun getHomeTimeline(): List<Status> {
        return mastodon.getHomeTimeline()
    }

    suspend fun getPublicTimeline(localOnly: Boolean = false): List<Status> {
        return mastodon.getPublicTimeline(localOnly = localOnly)
    }

    suspend fun postStatus(statusBody: StatusBody) =
        mastodon.postStatus(content = statusBody)
}