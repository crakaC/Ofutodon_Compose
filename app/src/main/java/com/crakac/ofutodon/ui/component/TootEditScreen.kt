package com.crakac.ofutodon.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crakac.ofutodon.R
import com.crakac.ofutodon.ui.theme.OfutodonTheme

@Composable
fun TootEditForm(
    modifier: Modifier = Modifier,
    state: TootEditState = rememberTootEditState(),
) {
    Surface(modifier.wrapContentHeight()) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
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
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_public),
                        contentDescription = "visibility"
                    )
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