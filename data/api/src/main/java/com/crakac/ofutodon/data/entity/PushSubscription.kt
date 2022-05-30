package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class PushSubscription(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("endpoint")
    val endpoint: String = "",
    @SerializedName("server_key")
    val serverKey: String = "",
    @SerializedName("alerts")
    val alerts: Map<String, Boolean> = emptyMap(),
)
