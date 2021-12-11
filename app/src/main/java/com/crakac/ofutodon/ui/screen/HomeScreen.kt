package com.crakac.ofutodon.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.crakac.ofutodon.MainViewModel
import com.crakac.ofutodon.R
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.ui.component.*
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
    pages: Array<TimelineName>
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
                    scope.launch {
                        pagerState.animateScrollToPage(index)
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
    val homeTimeline by viewModel.homeTimeline.observeAsState(emptyList())
    val localTimeline by viewModel.localTimeline.observeAsState(emptyList())

    val homeTimelineState = rememberTimelineState()
    val localTimelineState = rememberTimelineState()

    val pagerState = rememberPagerState()
    val pages = TimelineName.values()

    val onClickStatus = object : StatusCallback {
        override fun onClickStatus(status: Status) {
            Log.d("StatusCallback", "onClickStatus")
        }

        override fun onClickReply(status: Status) {
            Log.d("StatusCallback", "onClickReply")
        }

        override fun onClickFavourite(status: Status) {
            Log.d("StatusCallback", "onClickFavourite")
            viewModel.favourite(status.id)
        }

        override fun onClickBoost(status: Status) {
            Log.d("StatusCallback", "onClickBoost")
            viewModel.boost(status.id)
        }

        override fun onClickMore(status: Status) {
            Log.d("StatusCallback", "onClickMore")
        }
    }

    Column {
        PagerTab(pagerState, pages)
        HorizontalPager(
            count = pages.size,
            state = pagerState,
        ) { page ->
            when (pages[page]) {
                TimelineName.Home -> {
                    Timeline(
                        modifier = modifier,
                        statuses = homeTimeline,
                        onRefresh = {
                            homeTimelineState.isRefreshing = true
                            viewModel.refreshHomeTimeline {
                                homeTimelineState.isRefreshing = false
                            }
                        },
                        onClickStatus = onClickStatus,
                        state = homeTimelineState
                    )
                }
                TimelineName.Local -> {
                    Timeline(
                        modifier = modifier,
                        statuses = localTimeline,
                        onRefresh = {
                            localTimelineState.isRefreshing = true
                            viewModel.refreshLocalTimeline {
                                localTimelineState.isRefreshing = false
                            }
                        },
                        onClickStatus = onClickStatus,
                        state = localTimelineState
                    )
                }
                TimelineName.Debug -> DummyTimeline(modifier = modifier)
            }
        }
    }
}