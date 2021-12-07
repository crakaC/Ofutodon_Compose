package com.crakac.ofutodon.ui.component

import androidx.compose.runtime.*
import com.crakac.ofutodon.mastodon.entity.Status

const val MAX_TOOT_LENGTH = 50

class TootEditState {
    var text by mutableStateOf("")
    var visibility by mutableStateOf(Status.Visibility.Public)
    val attachmentIds = mutableStateListOf<Long>()

    fun isValid(): Boolean {
        return (text.isNotBlank() && text.length <= MAX_TOOT_LENGTH)
                || attachmentIds.isNotEmpty()
    }

    val remaining: Int
        get() = MAX_TOOT_LENGTH - text.length

    val isValidLength: Boolean
        get() = text.length <= MAX_TOOT_LENGTH
}

@Composable
fun rememberTootEditState() =
    remember {
        TootEditState()
    }