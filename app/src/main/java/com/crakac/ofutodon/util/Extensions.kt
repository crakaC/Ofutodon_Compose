package com.crakac.ofutodon.util

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import com.crakac.ofutodon.R
import com.crakac.ofutodon.mastodon.entity.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter


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

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

@Composable
fun LazyListState.OnAppearLastItem(onAppearLastItem: () -> Unit) {
    val isReachedToEnd by remember {
        derivedStateOf {
            layoutInfo.visibleItemsInfo.size < layoutInfo.totalItemsCount &&
                    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
        }
    }

    val currentOnAppearLastItem by rememberUpdatedState(onAppearLastItem)

    LaunchedEffect(Unit) {
        snapshotFlow { isReachedToEnd }
            .filter { it }
            .collect {
                currentOnAppearLastItem()
            }
    }
}