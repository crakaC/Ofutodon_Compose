package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName

data class Activity(
    @SerializedName("week")
    val week: String = "",
    @SerializedName("statuses")
    val statuses: Long = 0L,
    @SerializedName("logins")
    val logins: Long = 0L,
    @SerializedName("registrations")
    val registrations: Long = 0L,
)