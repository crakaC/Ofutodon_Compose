package com.crakac.ofutodon.ui.component

import androidx.compose.runtime.*
import com.crakac.ofutodon.mastodon.entity.Status

const val MAX_TOOT_LENGTH = 500

class TootEditState {
    var text by mutableStateOf("")
    var visibility by mutableStateOf(Status.Visibility.Public)
    var isSending by mutableStateOf(false)
    val attachmentIds = mutableStateListOf<Long>()

    fun canSendStatus(): Boolean {
        return !isSending && ((text.isNotBlank() && text.length <= MAX_TOOT_LENGTH)
                || attachmentIds.isNotEmpty())
    }

    val remaining: Int
        get() = MAX_TOOT_LENGTH - text.length

    val isValidLength: Boolean
        get() = text.length <= MAX_TOOT_LENGTH

    fun reset() {
        text = ""
        attachmentIds.clear()
    }
}

@Composable
fun rememberTootEditState() =
    remember {
        TootEditState()
    }