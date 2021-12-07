package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName

data class Marker(
    @SerializedName("home")
    val home: MarkerInfo = MarkerInfo(),
    @SerializedName("notifications")
    val notifications: MarkerInfo = MarkerInfo(),
)

data class MarkerInfo(
    @SerializedName("last_read_id")
    val lastReadId: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("version")
    val version: Long = 0L,
)
