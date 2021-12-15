package com.crakac.ofutodon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crakac.ofutodon.data.MastodonRepository
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.timeline.DummyTimelineState
import com.crakac.ofutodon.timeline.HomeTimelineState
import com.crakac.ofutodon.timeline.PublicTimelineState
import com.crakac.ofutodon.timeline.StatusTimelineState
import com.crakac.ofutodon.ui.component.TootEditState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MastodonRepository
) : ViewModel() {
    private val _homeTimeline = HomeTimelineState(repo, viewModelScope)
    private val _localTimeline = PublicTimelineState(repo, isLocalOnly = true, viewModelScope)
    private val _publicTimeline = PublicTimelineState(repo, isLocalOnly = false, viewModelScope)
    private val debugTimeline = DummyTimelineState(viewModelScope)

    private val _timelines = listOf(
        _homeTimeline,
        _localTimeline,
        _publicTimeline,
        debugTimeline
    )

    val timelines: List<StatusTimelineState>
        get() = _timelines

    fun favourite(id: Long) {
        viewModelScope.launch {
            val updated = repo.favourite(id)
            update(updated)
        }
    }

    fun boost(id: Long) {
        viewModelScope.launch {
            val updated = repo.boost(id).reblog!!
            update(updated)
        }
    }

    private fun update(updated: Status) {
        timelines.forEach { timeline ->
            timeline.update(updated)
        }
    }

    fun toot(state: TootEditState) {
        state.isSending = true
        viewModelScope.launch {
            try {
                val attachmentIds = if (state.attachments.any()) {
                    repo.uploadMediaAttachments(state.attachments).map { it.id }
                } else {
                    null
                }
                repo.postStatus(state.toStatusBody().copy(mediaIds = attachmentIds))
                state.reset()
            } catch (e: IOException) {
                Log.w("MainViewModel", "${e.message}")
            } finally {
                state.isSending = false
            }
        }
    }
}