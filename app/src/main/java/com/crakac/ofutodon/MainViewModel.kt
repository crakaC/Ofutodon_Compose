package com.crakac.ofutodon

import android.util.Log
import androidx.lifecycle.*
import com.crakac.ofutodon.data.MastodonRepository
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.mastodon.params.StatusBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MastodonRepository) : ViewModel() {
    private val _homeTimeline: MutableLiveData<List<Status>> = MutableLiveData()
    val homeTimeline: LiveData<List<Status>>
        get() {
            if (_homeTimeline.value == null) {
                refreshHomeTimeline()
            }
            return _homeTimeline
        }

    private val _localTimeline: MutableLiveData<List<Status>> = MutableLiveData()
    val localTimeline: LiveData<List<Status>>
        get() {
            if (_localTimeline.value == null) {
                refreshLocalTimeline()
            }
            return _localTimeline
        }

    private val timelines = listOf(_homeTimeline, _localTimeline)

    fun refreshHomeTimeline(postExecute: () -> Unit = {}) {
        viewModelScope.launch {
            _homeTimeline.postValue(repo.getHomeTimeline())
            postExecute()
        }
    }

    fun refreshLocalTimeline(postExecute: () -> Unit = {}) {
        viewModelScope.launch {
            _localTimeline.postValue(repo.getPublicTimeline(localOnly = true))
            postExecute()
        }
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
        timelines.forEach { timeline ->
            timeline.value?.indexOfFirst { it.id == id }?.let { index ->
                if (index < 0) return@let
                val copy = timeline.value!!.toMutableList()
                copy[index] = updated
                timeline.postValue(copy)
            }
        }
    }

    fun toot(content: String, onSuccess: () -> Unit = {}, finally: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                repo.postStatus(StatusBody(content = content))
                onSuccess()
            } catch (e: IOException) {
                Log.w("MainViewModel", "${e.message}")
            } finally {
                finally()
            }
        }
    }
}