package com.crakac.ofutodon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crakac.ofutodon.mastodon.entity.Attachment
import com.crakac.ofutodon.ui.attachment.AttachmentPreview
import com.crakac.ofutodon.ui.screen.EditScreen
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
//                Timeline(modifier = Modifier.fillMaxSize(), statuses = (1..100).map { DummyStatus.copy(id = it.toLong()) })
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
            EditScreen(navController)
        }
        composable(Screen.PreviewAttachments.name) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index")!!
            val attachments =
                backStackEntry.arguments?.getParcelableArray("attachments")
                    ?.toList() as List<Attachment>
            AttachmentPreview(attachments = attachments, selectedIndex = index)
        }
    }
}
