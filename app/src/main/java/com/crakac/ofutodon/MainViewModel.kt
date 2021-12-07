package com.crakac.ofutodon

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.crakac.ofutodon.mastodon.Mastodon
import com.crakac.ofutodon.mastodon.entity.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var mastodon: Mastodon
    val homeTimeline: LiveData<List<Status>> = liveData {
        emit(mastodon.getHomeTimeline())
    }

    val localTimeline: LiveData<List<Status>> = liveData {
        emit(mastodon.getPublicTimeline(localOnly = true))
    }
}