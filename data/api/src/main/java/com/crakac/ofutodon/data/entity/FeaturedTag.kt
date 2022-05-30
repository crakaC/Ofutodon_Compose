package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class FeaturedTag(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("statuses_count")
    val statusesCount: Long = 0L,
    @SerializedName("last_status_at")
    val lastStatusAt: String = "",
)
