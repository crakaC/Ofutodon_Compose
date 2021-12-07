package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName

data class Relationship(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("following")
    val following: Boolean = false,
    @SerializedName("requested")
    val requested: Boolean = false,
    @SerializedName("endorsed")
    val endorsed: Boolean = false,
    @SerializedName("followed_by")
    val followedBy: Boolean = false,
    @SerializedName("muting")
    val muting: Boolean = false,
    @SerializedName("muting_notifications")
    val mutingNotifications: Boolean = false,
    @SerializedName("showing_reblogs")
    val showingReblogs: Boolean = false,
    @SerializedName("notifying")
    val notifying: Boolean = false,
    @SerializedName("blocking")
    val blocking: Boolean = false,
    @SerializedName("domain_blocking")
    val domainBlocking: Boolean = false,
    @SerializedName("blocked_by")
    val blockedBy: Boolean = false,
    @SerializedName("note")
    val note: String = "",
)
