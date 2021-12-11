package com.crakac.ofutodon.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.crakac.ofutodon.MainViewModel
import com.crakac.ofutodon.ui.component.DefaultVisibility
import com.crakac.ofutodon.ui.component.EditFormCallback
import com.crakac.ofutodon.ui.component.TootEditForm
import com.crakac.ofutodon.ui.component.rememberTootEditState

@Composable
fun EditScreen(navController: NavHostController) {
    val viewModel: MainViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val editState = rememberTootEditState(DefaultVisibility)
    val callback = object : EditFormCallback {
        override fun onClickToot() {
            editState.isSending = true
            viewModel.toot(editState.toStatusBody(),
                onSuccess = { editState.reset() },
                finally = { editState.isSending = false }
            )
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
    ) { innerPadding ->
        TootEditForm(
            modifier = Modifier.padding(innerPadding),
            state = editState,
            callback = callback
        )
    }
}