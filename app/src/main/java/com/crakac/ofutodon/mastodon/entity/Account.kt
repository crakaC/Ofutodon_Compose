package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName
import java.net.IDN

data class Account(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("username")
    val username: String = "",
    @SerializedName("acct")
    val acct: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("display_name")
    val displayName: String = "",
    @SerializedName("note")
    val note: String = "",
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("avatar_static")
    val avatarStatic: String = "",
    @SerializedName("header")
    val header: String = "",
    @SerializedName("header_static")
    val headerStatic: String = "",
    @SerializedName("locked")
    val locked: String = "",
    @SerializedName("emojis")
    val emojis: List<Emoji> = emptyList(),
    @SerializedName("discoverable")
    val discoverable: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("last_status_at")
    val lastStatusAt: String = "",
    @SerializedName("statuses_count")
    val statusesCount: Long = 0L,
    @SerializedName("followers_count")
    val followersCount: Long = 0L,
    @SerializedName("following_count")
    val followingCount: Long = 0L,
    @SerializedName("moved")
    val moved: Account? = null,
    @SerializedName("fields")
    val fields: List<Field> = emptyList(),
    @SerializedName("bot")
    val bot: Boolean = false,
    @SerializedName("source")
    val source: Source? = null,
    @SerializedName("suspended")
    val suspended: Boolean = false,
    @SerializedName("mute_expires_at")
    val muteExpiresAt: String = "",
) {
    val unicodeAcct: String
        get() {
            val splitted = acct.split("@")
            return if (splitted.size == 1) {
                acct
            } else {
                splitted[0] + "@" + IDN.toUnicode(splitted[1])
            }
        }
}