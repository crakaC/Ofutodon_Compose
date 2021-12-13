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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.ui.theme.DarkGray
import com.crakac.ofutodon.ui.theme.OfutodonTheme
import com.crakac.ofutodon.util.OnAppearLastItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

enum class TimelineType {
    Home,
    Local,
    Debug,
}

@Composable
fun Timeline(
    modifier: Modifier = Modifier,
    statuses: List<Status>,
    state: TimelineState = rememberTimelineState(),
) {
    if (statuses.isEmpty()) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    Modifier.size(48.dp)
                )
            } else {
                Button(onClick = state.onEmpty) {
                    Text("Reload")
                }
            }
        }
    } else {
        SwipeRefresh(
            state = state.refreshState,
            onRefresh = state.onRefresh
        ) {
            LazyColumn(state = state.scrollState, modifier = modifier.fillMaxHeight()) {
                itemsIndexed(
                    items = statuses,
                    key = { _, status -> status.id })
                { index, status ->
                    StatusContent(status, state.onClickStatus)
                    if (index < statuses.lastIndex) {
                        Divider(color = DarkGray, thickness = Dp(0.5f))
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}

class TimelineState(
    val scrollState: LazyListState,
    val refreshState: SwipeRefreshState,
    /* loadingState is shared with ViewModel. It seems bad. */
    loadingState: MutableState<Boolean>,
    val onEmpty: () -> Unit = {},
    val onRefresh: () -> Unit = {},
    val onClickStatus: StatusCallback = StatusCallback.Default
) {
    val isLoading by loadingState
}

@Composable
fun rememberTimelineState(
    loadingState: MutableState<Boolean> = mutableStateOf(false),
    onEmpty: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onLastItemAppeared: () -> Unit = {},
    onClickStatus: StatusCallback = StatusCallback.Default
): TimelineState {
    val scrollState = rememberLazyListState().apply { OnAppearLastItem(onLastItemAppeared) }
    val refreshState = rememberSwipeRefreshState(loadingState.value)
    return remember {
        TimelineState(
            scrollState = scrollState,
            refreshState = refreshState,
            loadingState = loadingState,
            onEmpty = onEmpty,
            onRefresh = onRefresh,
            onClickStatus = onClickStatus
        )
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