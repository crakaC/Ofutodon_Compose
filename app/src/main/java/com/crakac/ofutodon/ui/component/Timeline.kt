package com.crakac.ofutodon.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Dp.Companion.Hairline
import androidx.compose.ui.unit.dp
import com.crakac.ofutodon.mastodon.entity.Account
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.ui.theme.DarkGray
import com.crakac.ofutodon.ui.theme.OfutodonTheme

@Composable
fun Timeline(statuses: List<Status>, scrollState: LazyListState = rememberLazyListState(), modifier: Modifier = Modifier) {
    val lastIndex = statuses.lastIndex
    LazyColumn(state = scrollState, modifier = modifier) {
        itemsIndexed(statuses, { _, status -> status.id }) { index, status ->
            StatusContent(status)
            if (index < lastIndex) {
                Divider(color = DarkGray, thickness = Dp(0.5f))
            }
        }
    }
}

@Preview
@Composable
private fun TimelinePreview() {
    val status = Status(
        account = Account(
            username = "user",
            displayName = "Lorem",
            acct = "local.host",
            avatar = "https://developer.android.com/images/brand/Android_Robot.png"
        ),
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        createdAt = "2021-12-06T09:26:11.384Z",
        repliesCount = 0L, reblogsCount = 1L, favouritesCount = 3L
    )
    val statuses = mutableListOf<Status>()
    repeat(10) {
        statuses.add(status.copy(id = it.toLong()))
    }
    OfutodonTheme {
        Timeline(statuses)
    }
}