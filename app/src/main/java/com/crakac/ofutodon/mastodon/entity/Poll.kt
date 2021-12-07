package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName

data class Poll(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("expires_at")
    val expiresAt: String = "",
    @SerializedName("expired")
    val expired: Boolean = false,
    @SerializedName("multiple")
    val multiple: Boolean = false,
    @SerializedName("votes_count")
    val votesCount: Int = 0,
    @SerializedName("voted")
    val voted: Boolean = false,
    @SerializedName("own_votes")
    val ownVotes: List<Int> = emptyList(),
    @SerializedName("options")
    val options: List<PollAnswer> = emptyList(),
    @SerializedName("emojis")
    val emojis: List<Emoji> = emptyList(),
)

data class PollAnswer(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("votes_count")
    val votesCount: Int = 0,
)
