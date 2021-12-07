package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("note")
    val note: String = "",
    @SerializedName("fields")
    val fields: List<Field> = emptyList(),
    @SerializedName("privacy")
    val privacy: String = Status.Visibility.UnListed.value,
    @SerializedName("sensitive")
    val sensitive: Boolean = false,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("follow_requests_count")
    val followRequestsCount: Int = 0,
)