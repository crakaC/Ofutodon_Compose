package com.crakac.ofutodon.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.crakac.ofutodon.MainViewModel
import com.crakac.ofutodon.R
import com.crakac.ofutodon.data.entity.Attachment
import com.crakac.ofutodon.data.entity.Status
import com.crakac.ofutodon.ui.LogCompositions
import com.crakac.ofutodon.ui.attachment.AttachmentPreview
import com.crakac.ofutodon.ui.attachment.AttachmentPreviewState
import com.crakac.ofutodon.ui.attachment.rememberAttachmentPreviewState
import com.crakac.ofutodon.ui.component.StatusCallback
import com.crakac.ofutodon.ui.component.Timeline
import com.crakac.ofutodon.util.showToast
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val previewState = rememberAttachmentPreviewState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.Edit.name)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "toot",
                )
            }
        },
    ) { innerPadding ->
        HomeScreenContent(Modifier.padding(innerPadding), previewState)
    }
    AttachmentPreview(previewState)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerTab(
    pagerState: PagerState,
    tabs: List<AnnotatedString>,
    onClickSelectedTab: (page: Int) -> Unit = {},
) {
    LogCompositions(tag = "PagerTab")
    val currentPage = pagerState.currentPage
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
            )
        },
    ) {
        tabs.forEachIndexed { index, tab ->
            val selected = index == currentPage
            Tab(
                text = { Text(text = tab) },
                selected = index == currentPage,
                onClick = {
                    if (selected) {
                        onClickSelectedTab(index)
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    previewState: AttachmentPreviewState,
) {
    val viewModel: MainViewModel = hiltViewModel()
    val timelines = viewModel.timelines

    LaunchedEffect(Unit) {
        timelines.forEach {
            if (it.data.isEmpty()) {
                it.refresh()
            }
        }
    }

    val context = LocalContext.current
    val onClickStatus = object : StatusCallback {
        override fun onClickStatus(status: Status) {
            context.showToast("onClickStatus")
        }

        override fun onClickReply(status: Status) {
            context.showToast("onClickReply")
        }

        override fun onClickFavourite(status: Status) {
            viewModel.favourite(status.id)
        }

        override fun onClickBoost(status: Status) {
            viewModel.boost(status.id)
        }

        override fun onClickMore(status: Status) {
            context.showToast("onClickMore")
        }

        override fun onClickAttachment(index: Int, attachments: List<Attachment>) {
            previewState.showPreview(index, attachments)
        }
    }

    val scrollState = timelines.map {
        rememberLazyListState()
    }

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column {
        PagerTab(pagerState, timelines.map { it.getName() }, onClickSelectedTab = { page ->
            scope.launch {
                // Scroll to top
                scrollState[page].animateScrollToItem(0)
            }
        })
        HorizontalPager(
            count = timelines.size,
            state = pagerState,
        ) { page ->
            val timeline = timelines[page]
            Timeline(
                modifier = modifier,
                statuses = timeline.data,
                loadingState = timeline.loadingState,
                refreshState = timeline.swipeRefreshState,
                scrollState = scrollState[page],
                onEmpty = { timeline.refresh() },
                onRefresh = { timeline.fetchNext() },
                onLastItemAppeared = { timeline.fetchPrevious() },
                onClickStatus = onClickStatus,
            )
        }
    }
}
