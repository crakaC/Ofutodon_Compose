package com.crakac.ofutodon.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crakac.ofutodon.R
import com.crakac.ofutodon.ui.theme.OfutodonTheme
import com.crakac.ofutodon.ui.theme.White50

const val MAX_TOOT_LENGTH = 500

@Composable
fun TootEditForm() {
    var text by remember { mutableStateOf("") }
    Surface(Modifier.height(IntrinsicSize.Min).wrapContentHeight()) {
        Column(modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                ,
                placeholder = { Text("今なにしてる？") },
                value = text,
                onValueChange = {
                    text = it
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
                        contentDescription = "attach file"
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_public),
                        contentDescription = "attach file"
                    )
                }
                TextButton(modifier = Modifier.size(48.dp), onClick = { /*TODO*/ }) {
                    Text("CW")
                }
                Spacer(Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = (MAX_TOOT_LENGTH - text.length).toString()
                )
            }
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = { /*TODO*/ }) {
                CompositionLocalProvider(LocalContentColor provides White50) {
                    Icon(painter = painterResource(id = R.drawable.ic_send), "Toot!")
                    Spacer(Modifier.width(4.dp))
                    Text("トゥート!")
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