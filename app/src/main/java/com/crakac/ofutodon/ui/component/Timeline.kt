package com.crakac.ofutodon.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.crakac.ofutodon.data.entity.Status
import com.crakac.ofutodon.ui.LogCompositions
import com.crakac.ofutodon.ui.theme.DarkGray
import com.crakac.ofutodon.ui.theme.OfutodonTheme
import com.crakac.ofutodon.util.OnAppearLastItem
import com.crakac.ofutodon.util.recomposeHighlighter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun Timeline(
    modifier: Modifier = Modifier,
    statuses: List<Status>,
    loadingState: State<Boolean> = mutableStateOf(false),
    refreshState: SwipeRefreshState = rememberSwipeRefreshState(isRefreshing = false),
    scrollState: LazyListState = rememberLazyListState(),
    onEmpty: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onLastItemAppeared: () -> Unit = {},
    onClickStatus: StatusCallback = StatusCallback.Default,
) {
    LogCompositions(tag = "Timeline")
    val isLoading by loadingState
    scrollState.OnAppearLastItem(onLastItemAppeared)
    if (statuses.isEmpty()) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    Modifier.size(48.dp),
                )
            } else {
                Button(onClick = onEmpty) {
                    Text("Reload")
                }
            }
        }
    } else {
        SwipeRefresh(
            state = refreshState,
            onRefresh = onRefresh,
        ) {
            LogCompositions(tag = "SwipeRefresh")
            LazyColumn(
                state = scrollState,
                modifier = modifier
                    .recomposeHighlighter()
                    .fillMaxHeight(),
            ) {
                itemsIndexed(
                    items = statuses,
                    contentType = { _, _ -> "status" },
                    key = { _, status -> status.id },
                ) { index, status ->
                    StatusContent(status, onClickStatus)
                    if (index < statuses.lastIndex) {
                        Divider(color = DarkGray, thickness = Dp(0.5f))
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            Modifier.size(32.dp),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TimelinePreview() {
    val statuses = (1..10).map { DummyStatus.copy(id = it.toLong()) }
    OfutodonTheme {
        Timeline(statuses = statuses)
    }
}
