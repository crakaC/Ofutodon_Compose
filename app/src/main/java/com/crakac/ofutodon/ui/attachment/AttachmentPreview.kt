package com.crakac.ofutodon.ui.attachment

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.crakac.ofutodon.mastodon.entity.Attachment
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AttachmentPreview(
    attachments: List<Attachment>,
    selectedIndex: Int
) {
    Log.d("AttachmentPreview", "index: $selectedIndex")
    val pagerState = rememberPagerState(selectedIndex)
    HorizontalPager(count = attachments.size, state = pagerState) { page ->
        val attachment = attachments[page]
        AsyncImage(
            model = attachment.url,
            contentDescription = attachment.description,
            modifier = Modifier.fillMaxSize()
        )
    }
}