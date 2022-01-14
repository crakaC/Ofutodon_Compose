package com.crakac.ofutodon.ui.component

import android.net.Uri
import androidx.compose.runtime.*
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.mastodon.params.StatusBody

const val MAX_TOOT_LENGTH = 500
const val MAX_ATTACHMENTS = 4
class TootEditState(initialVisibility: Status.Visibility = DefaultVisibility) {
    var text by mutableStateOf("")
    var isSending by mutableStateOf(false)
    val dropDownState = VisibilityDropDownState(initialVisibility)
    private val _attachments = mutableStateListOf<Uri>()
    val attachments: List<Uri>
        get() = _attachments

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
        _attachments.clear()
    }

    fun toStatusBody() =
        StatusBody(
            content = text,
            visibility = dropDownState.visibility
        )

    fun addAttachments(uris: List<Uri>): Boolean {
        val beforeSize = attachments.size
        _attachments.addAll(uris.take(MAX_ATTACHMENTS - beforeSize))
        return beforeSize + uris.size <= MAX_ATTACHMENTS
    }

    fun removeAttachment(index: Int) {
        _attachments.removeAt(index)
    }
}

@Composable
fun rememberTootEditState(initialVisibility: Status.Visibility): TootEditState {
    return remember {
        TootEditState(initialVisibility)
    }
}