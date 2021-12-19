package com.crakac.ofutodon.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.crakac.ofutodon.MainViewModel
import com.crakac.ofutodon.ui.attachment.AttachmentPreview
import com.crakac.ofutodon.ui.attachment.rememberAttachmentPreviewState
import com.crakac.ofutodon.ui.component.*
import com.crakac.ofutodon.util.showToast

@Composable
fun EditScreen(navController: NavHostController) {
    val viewModel: MainViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val editState = rememberTootEditState(DefaultVisibility)
    val previewState = rememberAttachmentPreviewState()
    val currentContext = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) {
            val addedSuccessfully = editState.addAttachments(uris = it)
            if (!addedSuccessfully) {
                currentContext.showToast("The number of attachments is up to $MAX_ATTACHMENTS")
            }
        }
    val callback = object : EditFormCallback {
        override fun onClickToot() {
            viewModel.toot(editState)
        }

        override fun onClickAddAttachment() {
            launcher.launch("image/*")
        }

        override fun onClickAttachment(index: Int, attachments: List<Uri>) {
            previewState.showPreview(index, attachments)
        }

        override fun onClickRemoveAttachment(index: Int) {
            editState.removeAttachment(index)
        }

        override fun onClickEditAttachment(index: Int) {
            currentContext.showToast("Edit")
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
        AttachmentPreview(previewState)
    }
}