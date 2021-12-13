package com.crakac.ofutodon.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.crakac.ofutodon.R
import com.crakac.ofutodon.mastodon.entity.Account
import com.crakac.ofutodon.mastodon.entity.Attachment
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.ui.theme.DarkGray
import com.crakac.ofutodon.ui.theme.OfutodonTheme
import com.crakac.ofutodon.ui.theme.Shapes
import com.crakac.ofutodon.util.iconResource

interface StatusCallback {
    fun onClickStatus(status: Status) {}
    fun onClickReply(status: Status) {}
    fun onClickFavourite(status: Status) {}
    fun onClickBoost(status: Status) {}
    fun onClickMore(status: Status) {}
    fun onClickAttachment(attachment: Attachment) {}
    companion object {
        val Default = object : StatusCallback {}
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun StatusContent(status: Status, callback: StatusCallback) {
    val account = status.account
    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { callback.onClickStatus(status) }
                .padding(start = 8.dp, end = 8.dp, top = 12.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = account.avatar),
                contentDescription = "icon",
                modifier = Modifier
                    .size(48.dp)
                    .clip(Shapes.medium)
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = account.displayName,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "@${account.acct}",
                        style = MaterialTheme.typography.body2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                        color = DarkGray
                    )
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(id = status.visibility.iconResource()),
                        contentDescription = "visibility",
                        modifier = Modifier.size(16.dp),
                        tint = DarkGray,
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = status.getRelativeTime(),
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.End,
                        color = DarkGray
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(status.spannedContent.toString(), style = MaterialTheme.typography.body1)
                if (status.mediaAttachments.any()) {
                    Attachments(
                        status.mediaAttachments,
                        onClickAttachment = callback::onClickAttachment
                    )
                }
                BottomIcons(status, callback)
            }
        }
    }
}

@Composable
fun Attachments(attachments: List<Attachment>, onClickAttachment: (Attachment) -> Unit = {}) {
    val rows = if (attachments.size >= 3) 2 else 1
    val spacer = 4.dp
    Row(
        modifier = Modifier
            .padding(vertical = spacer)
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(Shapes.medium),
    ) {
        // [1, 2, 3].reversed().chunked(2).reversed() -> [[1], [2, 3]]
        for ((col, attachmentRow) in attachments.reversed().chunked(rows).reversed().withIndex()) {
            if (col > 0) {
                Spacer(Modifier.width(spacer))
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                for ((index, attachment) in attachmentRow.withIndex()) {
                    if (index > 0) {
                        Spacer(Modifier.height(spacer))
                    }
                    Image(
                        painter = rememberAsyncImagePainter(attachment.previewUrl),
                        contentDescription = attachment.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clickable { onClickAttachment(attachment) },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun BottomIcons(status: Status, callback: StatusCallback) {
    val iconButtonSize = 40.dp
    val iconSize = 24.dp
    val spanWidth = 88.dp
    val textOffset = 4.dp
    val textWidth = spanWidth - iconButtonSize + textOffset * 2

    CompositionLocalProvider(LocalContentColor provides DarkGray) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                Modifier
                    .wrapContentHeight()
                    .width(spanWidth),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    modifier = Modifier.size(iconButtonSize),
                    onClick = { callback.onClickReply(status) }
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_reply),
                        contentDescription = "reply",
                        modifier = Modifier.size(iconSize)
                    )
                }
                if (status.repliesCount > 0) {
                    Text(
                        modifier = Modifier
                            .requiredWidth(textWidth),
                        text = status.repliesCount.toString(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .width(spanWidth),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    modifier = Modifier.size(iconButtonSize),
                    onClick = { callback.onClickBoost(status) }
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_boost),
                        contentDescription = "boost",
                        modifier = Modifier.size(iconSize)
                    )
                }
                if (status.reblogsCount > 0) {
                    Text(
                        modifier = Modifier
                            .requiredWidth(textWidth),
                        text = status.reblogsCount.toString(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .width(spanWidth),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    modifier = Modifier.size(iconButtonSize),
                    onClick = { callback.onClickFavourite(status) }
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_favourite),
                        contentDescription = "favourite",
                        modifier = Modifier.size(iconSize)
                    )
                }
                if (status.favouritesCount > 0) {
                    Text(
                        modifier = Modifier
                            .requiredWidth(textWidth),
                        text = status.favouritesCount.toString(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            IconButton(
                modifier = Modifier.size(iconButtonSize),
                onClick = { callback.onClickMore(status) }
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_more),
                    contentDescription = "more",
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}

val DummyStatus = Status(
    account = Account(
        username = "user",
        displayName = "Lorem",
        acct = "local.host",
        avatar = "https://developer.android.com/images/brand/Android_Robot.png"
    ),
    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    createdAt = "2021-12-06T09:26:11.384Z",
    repliesCount = 2L, reblogsCount = 1234567L, favouritesCount = 123L
)

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun StatusNightPreview() {
    val status = DummyStatus.copy(
        repliesCount = 1L,
        reblogsCount = 123L,
        favouritesCount = 123456L,
    )
    OfutodonTheme {
        StatusContent(status, StatusCallback.Default)
    }
}

@Preview
@Composable
private fun StatusPreview() {
    val status = DummyStatus.copy(
        repliesCount = 1L,
        reblogsCount = 12L,
        favouritesCount = 123456L,
    )
    OfutodonTheme {
        StatusContent(status, StatusCallback.Default)
    }
}