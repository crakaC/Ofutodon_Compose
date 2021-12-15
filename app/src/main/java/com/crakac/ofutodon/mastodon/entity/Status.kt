package com.crakac.ofutodon.mastodon.entity

import androidx.core.text.HtmlCompat
import com.crakac.ofutodon.util.Util
import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("id")
    val id: Long = -1L,
    @SerializedName("uri")
    val uri: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("account")
    val account: Account = Account(),
    @SerializedName("content")
    val content: String = "",
    @SerializedName("visibility")
    val visibility: Visibility = Visibility.Public,
    @SerializedName("sensitive")
    val sensitive: Boolean = false,
    @SerializedName("spoiler_text")
    val spoilerText: String = "",
    @SerializedName("media_attachments")
    val mediaAttachments: List<Attachment> = emptyList(),
    @SerializedName("application")
    val application: Application = Application(),
    @SerializedName("mentions")
    val mentions: List<Mention> = emptyList(),
    @SerializedName("tags")
    val tags: List<Tag> = emptyList(),
    @SerializedName("emojis")
    val emojis: List<Emoji> = emptyList(),
    @SerializedName("reblogs_count")
    val reblogsCount: Long = 0L,
    @SerializedName("favourites_count")
    val favouritesCount: Long = 0L,
    @SerializedName("replies_count")
    val repliesCount: Long = 0L,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("in_reply_to_account_id")
    val inReplyToAccountId: Long? = null,
    @SerializedName("in_reply_to_id")
    val inReplyToId: Long? = null,
    @SerializedName("reblog")
    val reblog: Status? = null,
    @SerializedName("poll")
    val poll: Poll? = null,
    @SerializedName("card")
    val card: Card? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("reblogged")
    val isReblogged: Boolean = false,
    @SerializedName("favourited")
    val isFavourited: Boolean = false,
    @SerializedName("muted")
    val isMuted: Boolean = false,
    @SerializedName("bookmarked")
    val isBookmarked: Boolean = false,
    @SerializedName("pinned")
    val isPinned: Boolean = false,
) {
    enum class Visibility {
        @SerializedName("public")
        Public,

        @SerializedName("unlisted")
        UnListed,

        @SerializedName("private")
        Private,

        @SerializedName("direct")
        Direct,
    }

    val isReblog: Boolean
        get() = reblog != null

    val isBoostable: Boolean
        get() = visibility == Visibility.Public || visibility == Visibility.UnListed

    val isFavouritedWithOriginal: Boolean
        get() {
            return reblog?.isFavourited ?: isFavourited
        }

    val isBoostedWithOriginal: Boolean
        get() {
            return reblog?.isReblogged ?: isReblogged
        }

    fun hasSpoileredText() = spoilerText.isNotEmpty()

    private val createdAtMillis: Long by lazy { Util.parseCreatedAt(createdAt) }
    fun getRelativeTime() = Util.getRelativeTimeSpanString(createdAtMillis).toString()

    val spannedContent by lazy {
        HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_COMPACT).trim()
    }
}