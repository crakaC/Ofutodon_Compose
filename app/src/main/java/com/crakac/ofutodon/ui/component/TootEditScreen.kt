package com.crakac.ofutodon.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crakac.ofutodon.MainViewModel
import com.crakac.ofutodon.R
import com.crakac.ofutodon.ui.theme.OfutodonTheme
import com.crakac.ofutodon.util.iconResource

@Composable
fun TootEditForm(
    modifier: Modifier = Modifier,
    state: TootEditState = rememberTootEditState(),
) {
    val viewModel: MainViewModel = hiltViewModel()
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
                    dropDownState.expanded = true
                }) {
                    Icon(
                        painter = painterResource(dropDownState.visibility.iconResource()),
                        contentDescription = "visibility"
                    )
                    VisibilityDropDownMenu(state = dropDownState)
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
                enabled = state.canSendStatus(),
                onClick = {
                    state.isSending = true
                    viewModel.toot(state.text,
                        onSuccess = { state.reset() },
                        finally = { state.isSending = false }
                    )
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