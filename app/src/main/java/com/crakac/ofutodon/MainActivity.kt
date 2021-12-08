package com.crakac.ofutodon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crakac.ofutodon.mastodon.entity.Account
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.ui.component.Timeline
import com.crakac.ofutodon.ui.component.TootEditForm
import com.crakac.ofutodon.ui.theme.OfutodonTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfutodonTheme {
                OfutonApp()
//                Timeline(modifier = Modifier.fillMaxSize(), statuses = DebugStatuses)
            }
        }
    }
}

@Composable
fun OfutonApp(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    AppNavHost(navController = navController, viewModel = viewModel)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Timelines.name,
        modifier = modifier
    ) {
        composable(Screen.Timelines.name) {
            HomeScreen(navController, viewModel)
        }
        composable(Screen.Edit.name) {
            TootEditForm(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {
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
        HomeScreenContent(viewModel, Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenContent(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val homeTimeline by viewModel.homeTimeline.observeAsState()
    val localTimeline by viewModel.localTimeline.observeAsState()

    val pagerState = rememberPagerState()
    val pages = TimelineName.values()

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

        HorizontalPager(
            count = pages.size,
            state = pagerState
        ) { page ->
            when (pages[page]) {
                TimelineName.Home -> homeTimeline?.let { home ->
                    Timeline(
                        modifier = modifier,
                        statuses = home
                    )
                }
                TimelineName.Local -> localTimeline?.let { local ->
                    Timeline(
                        modifier = modifier,
                        statuses = local
                    )
                }
                TimelineName.Debug -> Timeline(modifier = modifier, statuses = DebugStatuses)
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
        add(
            Status(
                id = it + 1L,
                account = account,
                content = "status: $it",
                createdAt = "2021-12-05T15:45:33.313Z"
            )
        )
    }
}

enum class Screen {
    Timelines,
    Edit,
}

enum class TimelineName {
    Home,
    Local,
    Debug,
}