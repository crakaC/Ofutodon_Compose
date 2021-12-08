package com.crakac.ofutodon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crakac.ofutodon.ui.component.TootEditForm
import com.crakac.ofutodon.ui.screen.HomeScreen
import com.crakac.ofutodon.ui.screen.Screen
import com.crakac.ofutodon.ui.theme.OfutodonTheme
import dagger.hilt.android.AndroidEntryPoint

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
fun OfutonApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.name,
    ) {
        composable(Screen.Home.name) {
            HomeScreen(navController)
        }
        composable(Screen.Edit.name) {
            TootEditForm(modifier = Modifier.fillMaxSize())
        }
    }
}
