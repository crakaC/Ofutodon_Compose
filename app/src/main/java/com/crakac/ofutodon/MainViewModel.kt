package com.crakac.ofutodon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crakac.ofutodon.mastodon.Mastodon
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.mastodon.params.StatusBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var mastodon: Mastodon
    private val _homeTimeline: MutableLiveData<List<Status>> = MutableLiveData()
    val homeTimeline: LiveData<List<Status>>
        get() {
            if (_homeTimeline.value == null) {
                viewModelScope.launch {
                    refreshHomeTimeline()
                }
            }
            return _homeTimeline
        }

    private val _localTimeline: MutableLiveData<List<Status>> = MutableLiveData()
    val localTimeline: LiveData<List<Status>>
        get() {
            if (_localTimeline.value == null) {
                viewModelScope.launch {
                    refreshLocalTimeline()
                }
            }
            return _localTimeline
        }

    suspend fun refreshHomeTimeline() {
        _homeTimeline.postValue(mastodon.getHomeTimeline())
    }

    suspend fun refreshLocalTimeline() {
        _localTimeline.postValue(mastodon.getPublicTimeline(localOnly = true))
    }

    suspend fun toot(content: String) {
        mastodon.postStatus(StatusBody(content = content))
    }
}