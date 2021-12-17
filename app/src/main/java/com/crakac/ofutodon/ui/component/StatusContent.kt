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
import androidx.compose.ui.res.stringResource
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
import com.crakac.ofutodon.ui.theme.*
import com.crakac.ofutodon.util.iconResource

interface StatusCallback {
    fun onClickStatus(status: Status) {}
    fun onClickReply(status: Status) {}
    fun onClickFavourite(status: Status) {}
    fun onClickBoost(status: Status) {}
    fun onClickMore(status: Status) {}
    fun onClickAttachment(index: Int, attachments: List<Attachment>) {}

    companion object {
        val Default = object : StatusCallback {}
    }
}

private val IconSize = 54.dp
private val OriginalIconSize = 40.dp
private val BoostedByIconSize = 24.dp

@OptIn(ExperimentalCoilApi::class)
@Composable
fun StatusContent(status: Status, callback: StatusCallback) {
    val originalStatus = status.reblog ?: status
    val originalAccount = originalStatus.account
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { callback.onClickStatus(status) }
                .padding(start = 8.dp, end = 8.dp, top = 12.dp)
        ) {
            if (status.isReblog) {
                BoostedBy(status.account.displayName)
            }
            Row {
                AccountIcon(status)
                Spacer(Modifier.width(8.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = originalAccount.displayName,
                            style = MaterialTheme.typography.h6
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "@${originalAccount.unicodeAcct}",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f),
                            color = DarkGray
                        )
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = originalStatus.visibility.iconResource()),
                            contentDescription = "visibility",
                            modifier = Modifier.size(16.dp),
                            tint = DarkGray,
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = originalStatus.getRelativeTime(),
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.End,
                            color = DarkGray
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        originalStatus.spannedContent.toString(),
                        style = MaterialTheme.typography.body1
                    )
                    if (originalStatus.mediaAttachments.any()) {
                        Attachments(
                            originalStatus.mediaAttachments,
                            onClickAttachment = { index ->
                                callback.onClickAttachment(
                                    index,
                                    originalStatus.mediaAttachments
                                )
                            }
                        )
                    }
                    BottomIcons(originalStatus, callback)
                }
            }
        }
    }
}

@Composable
fun AccountIcon(status: Status) {
    val originalAccount = status.reblog?.account
    Box(Modifier.size(IconSize)) {
        if (originalAccount == null) {
            Image(
                painter = rememberAsyncImagePainter(model = status.account.avatar),
                contentDescription = "icon",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(Shapes.medium)
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(model = originalAccount.avatar),
                contentDescription = "original icon",
                modifier = Modifier
                    .size(OriginalIconSize)
                    .align(Alignment.TopStart)
                    .clip(Shapes.medium),
            )
            Image(
                painter = rememberAsyncImagePainter(model = status.account.avatar),
                contentDescription = "icon",
                modifier = Modifier
                    .size(BoostedByIconSize)
                    .align(Alignment.BottomEnd)
                    .clip(Shapes.medium),
            )
        }
    }
}

@Composable
fun BoostedBy(displayName: String) {
    CompositionLocalProvider(LocalContentColor provides DarkGray) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.width(IconSize)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_boost),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterEnd)
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.boosted_by, displayName),
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
    Spacer(Modifier.height(4.dp))
}

@Composable
fun Attachments(
    attachments: List<Attachment>,
    onClickAttachment: (index: Int) -> Unit = {}
) {
    val rows = if (attachments.size >= 3) 2 else 1
    val spacer = 4.dp
    Row(
        modifier = Modifier
            .padding(vertical = spacer)
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(Shapes.medium),
    ) {
        // [1, 2, 3] -> [[1], [2, 3]]
        for ((col, attachmentRow) in attachments.reversed().chunked(rows) { it.reversed() }
            .reversed().withIndex()) {
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
                            .clickable { onClickAttachment(attachments.indexOf(attachment)) },
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
                    onClick = { callback.onClickBoost(status) },
                    enabled = status.isBoostable,
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_boost),
                        contentDescription = "boost",
                        modifier = Modifier.size(iconSize),
                        tint = if (status.isBoostedWithOriginal) BoostBlue else LocalContentColor.current
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
                        modifier = Modifier.size(iconSize),
                        tint = if (status.isFavouritedWithOriginal) FavouriteYellow else LocalContentColor.current
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