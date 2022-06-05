package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("type")
    val type: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("account")
    val account: String = "",
    @SerializedName("status")
    val status: Status = Status(),
)
