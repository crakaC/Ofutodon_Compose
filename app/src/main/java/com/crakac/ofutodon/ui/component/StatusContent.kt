package com.crakac.ofutodon.ui.component

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.text.Spanned
import android.text.style.URLSpan
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.getSpans
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.crakac.ofutodon.R
import com.crakac.ofutodon.mastodon.entity.Account
import com.crakac.ofutodon.mastodon.entity.Attachment
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.ui.theme.*
import com.crakac.ofutodon.util.LocalPainterResource
import com.crakac.ofutodon.util.createImageRequest
import com.crakac.ofutodon.util.iconResource
import com.crakac.ofutodon.util.recomposeHighlighter

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

private val IconSize = 56.dp
private val OriginalIconSize = 40.dp
private val BoostedByIconSize = 32.dp

@Composable
fun StatusContent(status: Status, callback: StatusCallback) {
    val originalStatus = status.reblog ?: status
    Surface {
        Column(
            modifier = Modifier
                .recomposeHighlighter()
                .fillMaxWidth()
                .clickable { callback.onClickStatus(status) }
                .padding(start = 8.dp, end = 8.dp, top = 12.dp)
        ) {
            CompositionLocalProvider(LocalContentColor provides DarkGray) {
                if (status.isReblog) {
                    BoostedBy(status.account.displayName)
                    Spacer(Modifier.height(4.dp))
                }
                Row {
                    AccountIcon(status)
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Header(originalStatus)
                        Spacer(Modifier.height(4.dp))
                        Content(originalStatus.spannedContent)
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
}

@Composable
private fun Header(status: Status) {
    val account = status.account
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = account.displayName,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = "@${account.unicodeAcct}",
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(4.dp))
        Image(
            painter = LocalPainterResource.current.obtain(id = status.visibility.iconResource()),
            contentDescription = "visibility",
            modifier = Modifier.size(16.dp),
            colorFilter = ColorFilter.tint(LocalContentColor.current)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = status.getRelativeTime(),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.End,
        )
    }
}

@Composable
private fun Content(content: Spanned) {
    val spans = content.getSpans<URLSpan>()
    val urlSpanStyle = SpanStyle(color = MaterialTheme.colors.secondary)
    val annotatedString = buildAnnotatedString {
        var position = 0
        for (span in spans) {
            val start = content.getSpanStart(span)
            val end = content.getSpanEnd(span)
            append(content.substring(position, start))
            val tag = when (content[start]) {
                '#' -> "HashTag"
                '@' -> "Mention"
                else -> "URL"
            }
            pushStringAnnotation(tag, annotation = span.url)
            withStyle(urlSpanStyle) {
                append(content.substring(start, end))
            }
            pop()
            position = end
        }
        append(content.substring(position))
    }
    val contentColor = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
    val context = LocalContext.current
    ClickableText(
        annotatedString,
        style = MaterialTheme.typography.body1.copy(color = contentColor),
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.let { annotation ->
                Log.d("Clicked: ", annotation.item)
                if (annotation.tag == "URL") {
                    val url = Uri.parse(annotation.item)
                    context.startActivity(Intent(Intent.ACTION_VIEW, url))
                }
            }
        }
    )
}

@Composable
fun AccountIcon(status: Status) {
    val originalAccount = status.reblog?.account
    if (originalAccount == null) {
        AsyncImage(
            model = createImageRequest(status.account.avatar),
            contentDescription = "icon",
            modifier = Modifier
                .size(IconSize)
                .clip(Shapes.medium)
        )
    } else {
        Box(Modifier.size(IconSize)) {
            AsyncImage(
                model = createImageRequest(originalAccount.avatar),
                contentDescription = "original icon",
                modifier = Modifier
                    .size(OriginalIconSize)
                    .align(Alignment.TopStart)
                    .clip(Shapes.medium),
            )
            AsyncImage(
                model = createImageRequest(status.account.avatar),
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
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.width(IconSize)) {
            Image(
                painter = LocalPainterResource.current.obtain(id = R.drawable.ic_boost),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterEnd),
                colorFilter = ColorFilter.tint(LocalContentColor.current)
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

@Composable
fun Attachments(
    attachments: List<Attachment>,
    onClickAttachment: (index: Int) -> Unit = {}
) {
    val spacer = 4.dp

    val cols = if (attachments.size <= 2) attachments.size else (attachments.size + 1) / 2
    val attachmentsInColumns = List(cols) { mutableListOf<Attachment>() }

    // [1, 2, 3] -> [[1], [2, 3]]
    // [1, 2, 3, 4] -> [[1, 3], [2, 4]]
    if (attachments.size % cols != 0) {
        attachmentsInColumns[0].add(attachments[0])
        for ((index, attachment) in attachments.drop(1).withIndex()) {
            attachmentsInColumns[index % (cols - 1) + 1].add(attachment)
        }
    } else {
        for ((index, attachment) in attachments.withIndex()) {
            attachmentsInColumns[index % cols].add(attachment)
        }
    }
    Row(
        modifier = Modifier
            .aspectRatio(16f / 9f)
            .padding(vertical = spacer)
            .fillMaxWidth()
            .clip(Shapes.medium),
    ) {
        for ((colIndex, attachmentsInColumn) in attachmentsInColumns.withIndex()) {
            if (colIndex > 0) {
                Spacer(Modifier.width(spacer))
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                for ((index, attachment) in attachmentsInColumn.withIndex()) {
                    if (index > 0) {
                        Spacer(Modifier.height(spacer))
                    }
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(attachment.previewUrl)
                            .crossfade(true)
                            .build(),
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
    val iconTint = ColorFilter.tint(LocalContentColor.current)
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            Modifier
                .width(spanWidth),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier.size(iconButtonSize),
                onClick = { callback.onClickReply(status) }
            ) {
                Image(
                    painter = LocalPainterResource.current.obtain(id = R.drawable.ic_reply),
                    contentDescription = "reply",
                    modifier = Modifier.size(iconSize),
                    colorFilter = iconTint
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
                .width(spanWidth),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier.size(iconButtonSize),
                onClick = { callback.onClickBoost(status) },
                enabled = status.isBoostable,
            ) {
                Image(
                    painter = LocalPainterResource.current.obtain(id = R.drawable.ic_boost),
                    contentDescription = "boost",
                    modifier = Modifier.size(iconSize),
                    colorFilter = if (status.isBoostedWithOriginal) BoostBlueTint else iconTint
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
                .width(spanWidth),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier.size(iconButtonSize),
                onClick = { callback.onClickFavourite(status) }
            ) {
                Image(
                    painter = LocalPainterResource.current.obtain(id = R.drawable.ic_favourite),
                    contentDescription = "favourite",
                    modifier = Modifier.size(iconSize),
                    colorFilter = if (status.isFavouritedWithOriginal) FavouriteYellowTint else iconTint
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
            Image(
                painter = LocalPainterResource.current.obtain(id = R.drawable.ic_more),
                contentDescription = "more",
                modifier = Modifier.size(iconSize),
                colorFilter = iconTint
            )
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
    repliesCount = 2L, reblogsCount = 1234567L, favouritesCount = 123L,
    mediaAttachments = (1..7).map { Attachment(previewUrl = "https://developer.android.com/images/brand/Android_Robot.png") },
)

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun StatusNightPreview() {
    val status = DummyStatus.copy(
        repliesCount = 1L,
        reblogsCount = 123L,
        favouritesCount = 123456L,
        reblog = DummyStatus
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