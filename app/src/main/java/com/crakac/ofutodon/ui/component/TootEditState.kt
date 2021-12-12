package com.crakac.ofutodon.ui.component

import android.net.Uri
import androidx.compose.runtime.*
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.mastodon.params.StatusBody

const val MAX_TOOT_LENGTH = 500

class TootEditState(val dropDownState: VisibilityDropDownState) {
    var text by mutableStateOf("")
    var isSending by mutableStateOf(false)
    var attachments by mutableStateOf<List<Uri>>(emptyList())

    fun canSendStatus(): Boolean {
        return !isSending && ((text.isNotBlank() && text.length <= MAX_TOOT_LENGTH)
                || attachments.isNotEmpty())
    }

    val remaining: Int
        get() = MAX_TOOT_LENGTH - text.length

    val isValidLength: Boolean
        get() = text.length <= MAX_TOOT_LENGTH

    fun reset() {
        text = ""
        attachments = emptyList()
    }

    fun toStatusBody() =
        StatusBody(
            content = text,
            visibility = dropDownState.visibility
        )
}

@Composable
fun rememberTootEditState(initialVisibility: Status.Visibility): TootEditState {
    val dropDownState = rememberVisibilityDropDownState(initialVisibility)
    return remember {
        TootEditState(dropDownState = dropDownState)
    }
}