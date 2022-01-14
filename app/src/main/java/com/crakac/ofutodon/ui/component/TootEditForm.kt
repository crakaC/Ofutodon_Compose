package com.crakac.ofutodon.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.crakac.ofutodon.R
import com.crakac.ofutodon.ui.LogCompositions
import com.crakac.ofutodon.ui.theme.AttachmentActionBackGround
import com.crakac.ofutodon.ui.theme.OfutodonTheme
import com.crakac.ofutodon.ui.theme.Shapes
import com.crakac.ofutodon.util.iconResource
import kotlinx.coroutines.flow.filter

interface EditFormCallback {
    fun onClickCamera() {}
    fun onClickAddAttachment() {}
    fun onClickPoll() {}
    fun onClickContentWarning() {}
    fun onClickToot() {}
    fun onClickAttachment(index: Int, attachments: List<Uri>) {}
    fun onClickRemoveAttachment(index: Int) {}
    fun onClickEditAttachment(index: Int) {}

    companion object {
        val Default = object : EditFormCallback {}
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TootEditForm(
    modifier: Modifier = Modifier,
    state: TootEditState = rememberTootEditState(DefaultVisibility),
    callback: EditFormCallback = EditFormCallback.Default
) {
    LogCompositions(tag = "TootEditForm")
    val focusRequester = remember { FocusRequester() }
    Surface(modifier.wrapContentHeight()) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(1f)
                    .focusOrder(focusRequester),
                placeholder = { Text("今なにしてる？") },
                value = state.text,
                isError = !state.isValidLength,
                onValueChange = {
                    state.text = it
                },
                enabled = !state.isSending,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            val hasAttachment by remember {
                derivedStateOf { state.attachments.any() }
            }
            if (hasAttachment) {
                Attachments(
                    enabled = !state.isSending,
                    attachments = state.attachments,
                    onClick = { index -> callback.onClickAttachment(index, state.attachments) },
                    onClickRemove = callback::onClickRemoveAttachment,
                    onClickEdit = callback::onClickEditAttachment,
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { callback.onClickCamera() },
                    enabled = !state.isSending,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = "camera",
                    )
                }
                IconButton(
                    onClick = { callback.onClickAddAttachment() },
                    enabled = !state.isSending,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_photo),
                        contentDescription = "attach file"
                    )
                }
                IconButton(
                    onClick = { callback.onClickPoll() },
                    enabled = !state.isSending,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_poll),
                        contentDescription = "poll"
                    )
                }
                IconButton(
                    onClick = {
                        state.dropDownState.expanded = true
                    },
                    enabled = !state.isSending,
                ) {
                    Icon(
                        painter = painterResource(state.dropDownState.visibility.iconResource()),
                        contentDescription = "visibility"
                    )
                    VisibilityDropDownMenu(state = state.dropDownState)
                }
                IconButton(
                    onClick = { callback.onClickContentWarning() },
                    enabled = !state.isSending,
                ) {
                    Text("CW")
                }
                Spacer(Modifier.weight(1f))
                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .alpha(if (state.isSending) ContentAlpha.disabled else LocalContentAlpha.current),
                    text = state.remaining.toString(),
                    color = if (state.isValidLength) Color.Unspecified else MaterialTheme.colors.error,
                    style = MaterialTheme.typography.button,
                )
            }
            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp),
                enabled = state.canSendStatus(),
                onClick = {
                    callback.onClickToot()
                }
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_send), "Toot!")
                Spacer(Modifier.width(4.dp))
                Text("トゥート!")
            }
        }
    }
    val focusManager = LocalFocusManager.current
    LaunchedEffect(Unit) {
        // Request focus to TextField at first time
        focusRequester.requestFocus()

        // Hide IME when isSending == true
        snapshotFlow {
            state.isSending
        }.filter { it }.collect {
            focusManager.clearFocus()
        }
    }
}

private val AttachmentActionModifier = Modifier
    .padding(4.dp)
    .size(32.dp)
    .clip(CircleShape)
    .background(AttachmentActionBackGround)

@Composable
fun Attachments(
    attachments: List<Uri>,
    enabled: Boolean = true,
    onClick: (index: Int) -> Unit = {},
    onClickRemove: (index: Int) -> Unit = {},
    onClickEdit: (index: Int) -> Unit = {},
) {
    LazyRow(
        modifier = Modifier.wrapContentHeight(),
        state = rememberLazyListState(),
        contentPadding = PaddingValues(start = 8.dp, top = 8.dp, end = 8.dp),
    ) {
        itemsIndexed(attachments) { index, uri ->
            if (index > 0) {
                Spacer(Modifier.width(8.dp))
            }
            Box(
                Modifier
                    .size(160.dp)
                    .alpha(if (enabled) 1f else ContentAlpha.disabled)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(Shapes.medium)
                        .clickable(
                            enabled = enabled,
                            role = Role.Image,
                            onClick = { onClick(index) }),
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = AttachmentActionModifier
                        .align(Alignment.TopEnd)
                        .clickable(
                            enabled = enabled,
                            role = Role.Button,
                            onClick = { onClickRemove(index) }),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        contentDescription = "remove attachment $index"
                    )
                }
                Box(
                    modifier = AttachmentActionModifier
                        .align(Alignment.BottomEnd)
                        .clickable(
                            enabled = enabled,
                            role = Role.Button,
                            onClick = { onClickEdit(index) }),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_vert),
                        contentDescription = "edit attachment $index"
                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewNightTootEditForm() {
    OfutodonTheme {
        TootEditForm()
    }
}

@Preview(
    heightDp = 240
)
@Composable
private fun PreviewTootEditForm() {
    OfutodonTheme {
        TootEditForm()
    }
}