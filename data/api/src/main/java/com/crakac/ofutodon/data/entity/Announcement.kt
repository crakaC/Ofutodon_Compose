package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Announcement(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("text")
    val text: String = "",
    @SerializedName("published")
    val published: Boolean = false,
    @SerializedName("all_day")
    val allDay: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("read")
    val read: Boolean = false,
    @SerializedName("reactions")
    val reactions: List<com.crakac.ofutodon.data.entity.AnnouncementReaction> = emptyList(),
    @SerializedName("scheduled_at")
    val scheduledAt: String = "",
    @SerializedName("starts_at")
    val startsAt: String = "",
    @SerializedName("ends_at")
    val endsAt: String = "",
)

data class AnnouncementReaction(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("count")
    val count: Long = 0L,
    @SerializedName("me")
    val me: Boolean = false,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("static_url")
    val staticUrl: String = "",
)
