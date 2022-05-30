package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("note")
    val note: String = "",
    @SerializedName("fields")
    val fields: List<Field> = emptyList(),
    @SerializedName("privacy")
    val privacy: Status.Visibility = Status.Visibility.UnListed,
    @SerializedName("sensitive")
    val sensitive: Boolean = false,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("follow_requests_count")
    val followRequestsCount: Int = 0,
)