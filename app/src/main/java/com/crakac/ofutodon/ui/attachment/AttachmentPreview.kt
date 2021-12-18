package com.crakac.ofutodon.ui.attachment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
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
    AttachmentPreview(
        showPreview = state.showPreview,
        initialIndex = state.currentIndex,
        attachments = state.attachments,
        onBackKeyPressed = { state.hidePreview() }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AttachmentPreview(
    showPreview: Boolean,
    initialIndex: Int,
    attachments: List<Any>,
    onBackKeyPressed: () -> Unit = {}
) {
    if (!showPreview) return

    LogCompositions(tag = "AttachmentPreview")
    BackHandler {
        onBackKeyPressed()
    }
    val pagerState = rememberPagerState(initialIndex)
    HorizontalPager(count = attachments.size, state = pagerState) { page ->
        Box(
            Modifier
                .fillMaxSize()
                .background(PreviewBackGround)
                .pointerInput(Unit) {}
        ) {
            when (val attachment = attachments[page]) {
                is Attachment -> {
                    AsyncImage(
                        model = attachment.url,
                        contentDescription = attachment.description,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    AsyncImage(
                        model = attachment,
                        contentDescription = "attachment $page",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

class AttachmentPreviewState {
    var showPreview: Boolean by mutableStateOf(false)
    var currentIndex: Int by mutableStateOf(0)
    var attachments: List<Any> = emptyList()
    fun showPreview(index: Int, attachments: List<Any>) {
        showPreview = true
        currentIndex = index
        this.attachments = attachments
    }

    fun hidePreview() {
        showPreview = false
        currentIndex = 0
        attachments = emptyList()
    }
}

@Composable
fun rememberAttachmentPreviewState() = remember { AttachmentPreviewState() }