package com.crakac.ofutodon.ui.component

import androidx.compose.runtime.*
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.mastodon.params.StatusBody

const val MAX_TOOT_LENGTH = 500

class TootEditState(val dropDownState: VisibilityDropDownState) {
    var text by mutableStateOf("")
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

    fun toStatusBody() =
        StatusBody(
            content = text,
            mediaIds = if (attachmentIds.any()) attachmentIds else null,
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