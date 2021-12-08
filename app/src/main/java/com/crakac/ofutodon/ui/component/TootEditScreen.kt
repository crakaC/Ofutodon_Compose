package com.crakac.ofutodon.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crakac.ofutodon.R
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.ui.theme.OfutodonTheme

@Composable
fun TootEditForm(
    modifier: Modifier = Modifier,
    state: TootEditState = rememberTootEditState(),
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
            )
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_attach_file),
                        contentDescription = "attach file"
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_poll),
                        contentDescription = "poll"
                    )
                }
                val dropDownState = rememberVisibilityDropDownState()
                IconButton(onClick = {
                    dropDownState.expanded.value = true
                }) {
                    Icon(
                        painter = painterResource(dropDownState.visibility.value.iconResource()),
                        contentDescription = "visibility"
                    )
                    DropDown(state = dropDownState)
                }
                IconButton(onClick = { /*TODO*/ }) {
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
                enabled = state.isValid(),
                onClick = {}
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

class VisibilityDropDownState(initialVisibility: Status.Visibility) {
    var expanded = mutableStateOf(false)
    var visibility = mutableStateOf(initialVisibility)
}

@Composable
fun rememberVisibilityDropDownState(initialVisibility: Status.Visibility = Status.Visibility.Public) =
    remember { VisibilityDropDownState(initialVisibility) }

@Composable
fun DropDown(state: VisibilityDropDownState) {
    var expanded by state.expanded
    var selectedVisibility by state.visibility
    DropdownMenu(
        modifier = Modifier.wrapContentSize(),
        expanded = expanded, onDismissRequest = { expanded = false }) {
        Status.Visibility.values().forEach { visibility ->
            DropdownMenuItem(
                onClick = {
                    selectedVisibility = visibility
                    expanded = false
                }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = visibility.iconResource()),
                    contentDescription = visibility.name
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    stringResource(id = visibility.stringResource())
                )
            }
        }
    }
}

fun Status.Visibility.iconResource(): Int {
    return when (this) {
        Status.Visibility.Public -> R.drawable.ic_public
        Status.Visibility.UnListed -> R.drawable.ic_lock_open
        Status.Visibility.Private -> R.drawable.ic_lock
        Status.Visibility.Direct -> R.drawable.ic_direct
    }
}

fun Status.Visibility.stringResource(): Int {
    return when (this) {
        Status.Visibility.Public -> R.string.visibility_public
        Status.Visibility.UnListed -> R.string.visibility_unlisted
        Status.Visibility.Private -> R.string.visibility_private
        Status.Visibility.Direct -> R.string.visibility_direct
    }
}

@Preview
@Composable
private fun PreviewDropDown() {
    val state = rememberVisibilityDropDownState()
    state.expanded.value = true
    DropDown(state)
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