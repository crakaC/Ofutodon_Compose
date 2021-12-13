package com.crakac.ofutodon.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.crakac.ofutodon.MainViewModel
import com.crakac.ofutodon.R
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.ui.component.StatusCallback
import com.crakac.ofutodon.ui.component.Timeline
import com.crakac.ofutodon.ui.component.TimelineType
import com.crakac.ofutodon.ui.component.rememberTimelineState
import com.crakac.ofutodon.util.showToast
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.Edit.name)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "toot"
                )
            }
        }
    ) { innerPadding ->
        HomeScreenContent(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerTab(
    pagerState: PagerState,
    pages: Array<TimelineType>,
    onClickSelectedTab: (page: Int) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    TabRow(selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        pages.forEachIndexed { index, screen ->
            val selected = pagerState.currentPage == index
            Tab(
                text = { Text(text = screen.name) },
                selected = selected,
                onClick = {
                    if (selected) {
                        onClickSelectedTab(index)
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = hiltViewModel()

    val pages = TimelineType.values()// + (1..2).map { TimelineType.Debug }.toTypedArray()

    LaunchedEffect(Unit) {
        pages.forEach {
            viewModel.initTimeline(it)
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
    }

    val timelineState = pages.map { type ->
        rememberTimelineState(
            loadingState = viewModel.loadingState[type] ?: mutableStateOf(false),
            onEmpty = { viewModel.refresh(type) },
            onRefresh = { viewModel.refresh(type) },
            onLastItemAppeared = { viewModel.fetchNext(type) },
            onClickStatus = onClickStatus
        )
    }
    val timelineStatuses = pages.map { type ->
        viewModel.timelines[type]?.observeAsState(emptyList()) ?: mutableStateOf(emptyList())
    }

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column {
        PagerTab(pagerState, pages, onClickSelectedTab = { page ->
            scope.launch {
                // Scroll to top
                timelineState[page].scrollState.animateScrollToItem(0)
            }
        })
        HorizontalPager(
            count = pages.size,
            state = pagerState,
        ) { page ->
            Timeline(
                modifier = modifier,
                statuses = timelineStatuses[page].value,
                state = timelineState[page]
            )
        }
    }
}