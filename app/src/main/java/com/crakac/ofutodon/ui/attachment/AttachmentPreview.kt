package com.crakac.ofutodon.ui.attachment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.crakac.ofutodon.mastodon.entity.Attachment
import com.crakac.ofutodon.ui.LogCompositions
import com.crakac.ofutodon.ui.theme.PreviewBackGround
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AttachmentPreview(
    state: AttachmentPreviewState
) {
    LogCompositions(tag = "AttachmentPreview")
    BackHandler {
        state.showPreview = false
    }
    val pagerState = rememberPagerState(state.currentIndex)
    Box(
        Modifier
            .fillMaxSize()
            .background(PreviewBackGround)
    ) {
        HorizontalPager(count = state.attachments.size, state = pagerState) { page ->
            val attachment = state.attachments[page]
            AsyncImage(
                model = attachment.url,
                contentDescription = attachment.description,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

class AttachmentPreviewState {
    var showPreview: Boolean by mutableStateOf(false)
    var currentIndex: Int by mutableStateOf(0)
    var attachments: List<Attachment> = emptyList()
}

@Composable
fun rememberAttachmentPreviewState() = remember { AttachmentPreviewState() }