package com.crakac.ofutodon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crakac.ofutodon.data.MastodonRepository
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.timeline.DummyTimelineState
import com.crakac.ofutodon.timeline.HomeTimelineState
import com.crakac.ofutodon.timeline.PublicTimelineState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MastodonRepository
) : ViewModel() {
    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val homeTimeline = HomeTimelineState(repo, viewModelScope)
    private val localTimeline = PublicTimelineState(repo, isLocalOnly = true, viewModelScope)
    private val publicTimeline = PublicTimelineState(repo, isLocalOnly = false, viewModelScope)
    private val debugTimeline = DummyTimelineState(viewModelScope)

    val timelines = listOf(
        homeTimeline,
        localTimeline,
        publicTimeline,
        debugTimeline
    )

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

    override fun onCleared() {
        Log.d(TAG, "onCleared")
    }
}