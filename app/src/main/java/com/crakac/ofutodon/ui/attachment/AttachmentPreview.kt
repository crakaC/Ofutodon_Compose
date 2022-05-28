
package com.crakac.ofutodon.ui.attachment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.crakac.ofutodon.mastodon.entity.Attachment
import com.crakac.ofutodon.ui.LogCompositions
import com.crakac.ofutodon.ui.theme.DeepBlack
import com.crakac.ofutodon.ui.theme.PreviewBackGround
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
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
    Box(
        Modifier
            .fillMaxSize()
            .background(PreviewBackGround)
            .pointerInput(Unit) {}
    ) {
        HorizontalPager(count = attachments.size, state = pagerState) { page ->
            val model = when (val attachment = attachments[page]) {
                is Attachment -> attachment.url
                else -> attachment
            }
            AsyncImage(
                model = model,
                contentDescription = "attachment $page",
                modifier = Modifier.fillMaxSize(),
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            activeColor = MaterialTheme.colors.primary,
            inactiveColor = DeepBlack.copy(alpha = 0.5f)
        )
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