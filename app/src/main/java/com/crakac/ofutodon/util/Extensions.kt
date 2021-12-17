package com.crakac.ofutodon.util

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
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
                    firstVisibleItemIndex + layoutInfo.visibleItemsInfo.size == layoutInfo.totalItemsCount
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

fun NavController.navigate(
    route: String,
    args: Bundle
) {
    val routLink = NavDeepLinkRequest
        .Builder.fromUri(NavDestination.createRoute(route).toUri()).build()
    val deepLinkMatch = graph.matchDeepLink(routLink)
    if (deepLinkMatch != null) {
        val destinationId = deepLinkMatch.destination.id
        navigate(destinationId, args)
    } else {
        throw IllegalArgumentException("route $route is not found in navigation graph")
    }
}