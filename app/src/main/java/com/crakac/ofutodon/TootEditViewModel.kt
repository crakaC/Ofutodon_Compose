package com.crakac.ofutodon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crakac.ofutodon.data.MastodonRepository
import com.crakac.ofutodon.ui.component.TootEditState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TootEditViewModel @Inject constructor(private val repo: MastodonRepository) : ViewModel() {
    companion object {
        private val TAG = TootEditViewModel::class.java.simpleName
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

    override fun onCleared() {
        Log.d(TAG, "onCleared")
    }
}