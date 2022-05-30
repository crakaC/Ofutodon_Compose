package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Marker(
    @SerializedName("home")
    val home: com.crakac.ofutodon.data.entity.MarkerInfo = com.crakac.ofutodon.data.entity.MarkerInfo(),
    @SerializedName("notifications")
    val notifications: com.crakac.ofutodon.data.entity.MarkerInfo = com.crakac.ofutodon.data.entity.MarkerInfo(),
)

data class MarkerInfo(
    @SerializedName("last_read_id")
    val lastReadId: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("version")
    val version: Long = 0L,
)
