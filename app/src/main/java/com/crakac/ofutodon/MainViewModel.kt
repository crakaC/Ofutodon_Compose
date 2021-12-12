package com.crakac.ofutodon

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crakac.ofutodon.data.MastodonRepository
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.ui.component.DummyStatus
import com.crakac.ofutodon.ui.component.TimelineType
import com.crakac.ofutodon.ui.component.TootEditState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MastodonRepository) : ViewModel() {
    private val _homeTimeline: MutableLiveData<List<Status>> = MutableLiveData()
    private val _localTimeline: MutableLiveData<List<Status>> = MutableLiveData()
    private val debugTimeline: MutableLiveData<List<Status>> = MutableLiveData()

    private val _timelines = mapOf(
        TimelineType.Home to _homeTimeline,
        TimelineType.Local to _localTimeline,
        TimelineType.Debug to debugTimeline
    )

    val timelines: Map<TimelineType, LiveData<List<Status>>>
        get() = _timelines

    val loadingState = mapOf(
        TimelineType.Home to mutableStateOf(true),
        TimelineType.Local to mutableStateOf(true),
        TimelineType.Debug to mutableStateOf(false)
    )

    fun refresh(type: TimelineType) {
        viewModelScope.launch {
            loadingState[type]?.value = true
            when (type) {
                TimelineType.Home -> refreshHomeTimeline()
                TimelineType.Local -> refreshLocalTimeline()
                TimelineType.Debug -> {
                    delay(3000)
                }
            }
            loadingState[type]?.value = false
        }
    }

    private suspend fun refreshHomeTimeline() {
        _homeTimeline.postValue(repo.getHomeTimeline())
    }

    private suspend fun refreshLocalTimeline() {
        _localTimeline.postValue(repo.getPublicTimeline(localOnly = true))
    }

    fun favourite(id: Long) {
        viewModelScope.launch {
            val updated = repo.favourite(id)
            update(id, updated)
        }
    }

    fun boost(id: Long) {
        viewModelScope.launch {
            val updated = repo.boost(id).reblog!!
            update(id, updated)
        }
    }

    private fun update(id: Long, updated: Status) {
        _timelines.values.forEach { timeline ->
            timeline.value?.indexOfFirst { it.id == id }?.let { index ->
                if (index < 0) return@let
                val copy = timeline.value!!.toMutableList()
                copy[index] = updated
                timeline.postValue(copy)
            }
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

    fun initTimeline(type: TimelineType) {
        viewModelScope.launch {
            when (type) {
                TimelineType.Home -> refreshHomeTimeline()
                TimelineType.Local -> refreshLocalTimeline()
                TimelineType.Debug -> {
                    delay(3000)
                    debugTimeline.postValue((1..100).map { DummyStatus.copy(id = it.toLong()) })
                }
            }
            loadingState[type]?.value = false
        }
    }
}