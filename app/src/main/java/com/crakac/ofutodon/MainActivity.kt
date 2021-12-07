package com.crakac.ofutodon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.crakac.ofutodon.mastodon.entity.Account
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.ui.component.Timeline
import com.crakac.ofutodon.ui.theme.OfutodonTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfutodonTheme {
                MainScreen(viewModel)
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val homeTimeline by viewModel.homeTimeline.observeAsState()
    val localTimeline by viewModel.localTimeline.observeAsState()

    val pagerState = rememberPagerState()
    val pages = Screen.values()

    val scope = rememberCoroutineScope()

    Column {
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
                    text = { Text(text = screen.title) },
                    selected = selected,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            count = pages.size,
            state = pagerState
        ) { page ->
            when (pages[page]) {
                Screen.Home -> homeTimeline?.let { home -> Timeline(home) }
                Screen.Local -> localTimeline?.let { local -> Timeline(local) }
                Screen.Debug -> Timeline(DebugStatuses)
            }
        }
    }
}

val DebugStatuses = mutableListOf<Status>().apply {
    val account = Account(
        displayName = "からし\uD83C\uDF36",
        acct = "crakaC",
        avatar = "https://don.crakac.com/avatars/original/missing.png"
    )
    repeat(1000) {
        add(Status(id = it + 1L, account = account, content = "status: $it", createdAt = "2019-04-15T15:45:33.313Z"))
    }
}

enum class Screen(val title: String) {
    Home("Home"),
    Local("Local"),
    Debug("Debug"),
}