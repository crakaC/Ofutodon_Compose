package com.crakac.ofutodon.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.crakac.ofutodon.R
import com.crakac.ofutodon.ui.theme.OfutodonTheme
import com.crakac.ofutodon.util.iconResource

enum class EditType {
    OneShot,
    Continuance
}

interface EditFormCallback {
    fun onClickCamera() {}
    fun onClickAttachment() {}
    fun onClickPoll() {}
    fun onClickContentWarning() {}
    fun onClickToot() {}

    companion object {
        val Default = object : EditFormCallback {}
    }
}

@Composable
fun TootEditForm(
    modifier: Modifier = Modifier,
    state: TootEditState = rememberTootEditState(DefaultVisibility),
    callback: EditFormCallback = EditFormCallback.Default
) {
    val focusRequester = remember { FocusRequester() }
    Surface(modifier.wrapContentHeight()) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .focusOrder(focusRequester),
                placeholder = { Text("今なにしてる？") },
                value = state.text,
                isError = !state.isValidLength,
                onValueChange = {
                    state.text = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            val hasAttachment by remember {
                derivedStateOf { state.attachments.any() }
            }
            if (hasAttachment) {
                LazyRow(
                    modifier = Modifier.wrapContentHeight(),
                    state = rememberLazyListState(),
                ) {
                    items(state.attachments) { uri ->
                        Image(
                            painter = rememberImagePainter(uri),
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                                .clickable { },
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { callback.onClickCamera() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = "camera"
                    )
                }
                IconButton(onClick = { callback.onClickAttachment() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_photo),
                        contentDescription = "attach file"
                    )
                }
                IconButton(onClick = { callback.onClickPoll() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_poll),
                        contentDescription = "poll"
                    )
                }
                IconButton(onClick = {
                    state.dropDownState.expanded = true
                }) {
                    Icon(
                        painter = painterResource(state.dropDownState.visibility.iconResource()),
                        contentDescription = "visibility"
                    )
                    VisibilityDropDownMenu(state = state.dropDownState)
                }
                IconButton(onClick = { callback.onClickContentWarning() }) {
                    Text("CW")
                }
                Spacer(Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = state.remaining.toString(),
                    color = if (state.isValidLength) Color.Unspecified else MaterialTheme.colors.error,
                    style = MaterialTheme.typography.button
                )
            }
            Button(
                modifier = Modifier.align(Alignment.End),
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
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
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